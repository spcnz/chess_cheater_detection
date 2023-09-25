package yauza.chess

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.{Named, SessionWindows}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.state.Stores
import yauza.avro.message.chess._
import yauza.chess.aggregator.PlayerGameKpiAggregator
import yauza.chess.engine.Engine
import yauza.chess.joiner.MoveGameJoiner
import yauza.chess.mapper.ChessAnalysisMapper
import yauza.chess.processor.{GameScoreProcessor, UniqueUpdateProcessor}

import java.time.Duration

case class TopologyBuilder(chessEngine: Engine)(implicit config: Config)
    extends Serdes
    with ChessAnalysisMapper
    with MoveGameJoiner
    with PlayerGameKpiAggregator
    with LazyLogging {

  val builder: StreamsBuilder = new StreamsBuilder()

  def build: Topology = {

    builder.addStateStore(
      Stores.keyValueStoreBuilder(
        Stores.persistentKeyValueStore(config.chessAnalyzer.store.gameScore),
        stringSerde,
        gameScoreSerde
      )
    )
    builder.addStateStore(
      Stores.keyValueStoreBuilder(
        Stores.persistentKeyValueStore(config.chessAnalyzer.store.uniqueUpdates),
        stringSerde,
        intSerde
      )
    )
    val playerStream: KStream[String, Player] =
      builder.stream(config.chessAnalyzer.topic.input.player)

    val gameTable: KTable[String, Game] =
      builder
        .stream[String, Game](config.chessAnalyzer.topic.input.game)
        .process[String, Game](
          () => UniqueUpdateProcessor[String, Game](config.chessAnalyzer.store.uniqueUpdates),
          config.chessAnalyzer.store.uniqueUpdates
        )
        .toTable(
          Named.as(config.chessAnalyzer.store.game),
          Materialized
            .`with`(stringSerde, gameSerde)
        )

    val moveStream: KStream[String, MoveWithScore] =
      builder
        .stream[String, Move](config.chessAnalyzer.topic.input.move)
//        .process[String, Move](
//          () => UniqueUpdateProcessor[String, Move](config.chessAnalyzer.store.uniqueUpdates),
//          config.chessAnalyzer.store.uniqueUpdates
//        )
        .mapValues { move =>
          val (score, scoreType): (Long, String) = chessEngine.getPlayerScore(move.fen)
          val playerColor: String = chessEngine.getPointOfView(move.fen)
          mapMove(score, scoreType, playerColor)(move)
        }
        .peek((k, v) =>
          logger.whenDebugEnabled {
            logger.debug(s"Enriched move {key: $k} with Stockfish score: $v")
          }
        )

//    moveStream.groupByKey
//      .windowedBy(SessionWindows.ofInactivityGapWithNoGrace(Duration.ofMinutes(5)))
//      .count()
//      .toStream(Named.as("processed-move-count"))
//      .peek((k, v) =>
//        logger.whenDebugEnabled {
//          logger.debug(s"Produced count for move {key: ${k
//              .key()}}: and window: ${k.window().start()} - ${k.window().end()} - ${k.window().end()}")
//        }
//      )
//      .selectKey((w, value) => w.key())
//      .to("test-topic")

    val playerMoveStream = moveStream
      .join(gameTable)(joinMoveWithGame)
      .process[String, PlayerMove](
        () => GameScoreProcessor(config.chessAnalyzer.store.gameScore),
        config.chessAnalyzer.store.gameScore
      )
      .repartition(
        Repartitioned
          .`with`(config.chessAnalyzer.topic.sink.playerMoveScore)
      )

    playerMoveStream
      .groupBy((_, value) => value.id + "|" + value.gameId)
      .windowedBy(SessionWindows.ofInactivityGapWithNoGrace(Duration.ofMinutes(5)))
      .aggregate(initializer =
        PlayerGameKpi(
          id = "-1",
          username = "-1",
          gameId = "-1"
        )
      )(aggregate, merge)(Materialized.`with`(stringSerde, playerGameKpiSerde))
      .toStream(Named.as("processed-player-game-kpi"))
      .peek((k, v) =>
        logger.whenDebugEnabled {
          logger.debug(s"Enriched player game {key: $k} with KPI: $v")
        }
      )
      .filter((_, v) => v != null)
      .selectKey((window, value) => value.id)
      .to("kpi-topic")

    builder.build()
  }

}
