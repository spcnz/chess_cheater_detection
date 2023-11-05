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
import yauza.chess.aggregator.GameKpiAggregator
import yauza.chess.engine.Engine
import yauza.chess.joiner.{GameKpiGameResultJoiner, MoveGameJoiner}
import yauza.chess.mapper.ChessAnalysisMapper
import yauza.chess.processor.{GameScoreProcessor, PlayerKpiProcessor, UniqueUpdateProcessor}

import java.time.Duration

case class TopologyBuilder(chessEngine: Engine)(implicit config: Config)
    extends Serdes
    with ChessAnalysisMapper
    with MoveGameJoiner
    with GameKpiGameResultJoiner
    with GameKpiAggregator
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

    val playerTable: KTable[String, PlayerKpi] =
      builder
        .stream[String, Player](config.chessAnalyzer.topic.input.player)
        .mapValues(mapPlayerKpi)
        .peek((k, _) =>
          logger.whenDebugEnabled {
            logger.debug(s"Initialized player KPI for player: $k")
          }
        )
        .toTable(
          Named.as(config.chessAnalyzer.store.playerKpi),
          Materialized.as(config.chessAnalyzer.store.playerKpi)(stringSerde, playerKpiSerde)
        )

    val gameResultTable: KTable[String, GameResult] =
      builder
        .table[String, GameResult](config.chessAnalyzer.topic.input.gameResult)

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
            .as(config.chessAnalyzer.store.game)(stringSerde, gameSerde)
        )

    val moveStream: KStream[String, MoveWithScore] =
      builder
        .stream[String, Move](config.chessAnalyzer.topic.input.move)
        .process[String, Move](
          () => UniqueUpdateProcessor[String, Move](config.chessAnalyzer.store.uniqueUpdates),
          config.chessAnalyzer.store.uniqueUpdates
        )
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

    val moveGameStream: KStream[String, PlayerMove] =
      moveStream
        .join(gameTable)(joinMoveWithGame)
        .process[String, PlayerMove](
          () => GameScoreProcessor(config.chessAnalyzer.store.gameScore),
          config.chessAnalyzer.store.gameScore
        )
        .repartition(
          Repartitioned
            .`with`(config.chessAnalyzer.topic.sink.playerMoveScore)
        )
        .peek((k, v) =>
          logger.whenDebugEnabled {
            logger.debug(s"Joined move score ${v.lastMove} : ${v.score} with game: $k")
          }
        )

    val gameKpiStream =
      moveGameStream
        .groupBy((_, value) => value.id + "|" + value.gameId)
        .windowedBy(SessionWindows.ofInactivityGapWithNoGrace(Duration.ofSeconds(5)))
        .aggregate(initializer =
          GameKpi(
            id = "-1",
            username = "-1",
            gameId = "-1"
          )
        )(aggregate, merge)(Materialized.`with`(stringSerde, gameKpiSerde))
        .toStream(Named.as("processed-player-game-kpi"))
        .peek((k, v) =>
          logger.whenDebugEnabled {
            logger.debug(s"Enriched player game {key: $k} with KPI: $v")
          }
        )
        .filter((_, v) => v != null)
        .selectKey((window, value) => value.gameId)
        .repartition(
          Repartitioned
            .`with`(config.chessAnalyzer.topic.sink.gameKpi)
        )

    val playerKpi: KStream[String, PlayerKpi] = gameKpiStream
      .toTable(Named.as("game-kpi-table"))
      .leftJoin(gameResultTable)(joinGameKpiWithGameResult)
      .toStream(Named.as("game-kpi-with-result"))
      .process[String, PlayerKpi](
        () => PlayerKpiProcessor(config.chessAnalyzer.store.playerKpi),
        config.chessAnalyzer.store.playerKpi
      )
      .selectKey((_, value) => value.id)

    playerKpi
      .to(config.chessAnalyzer.topic.sink.playerKpi)

    playerKpi
      .filter((_, kpi) =>
        kpi.accuracies.count(gameAcc =>
          gameAcc._2 > (kpi.meanPlayerAccuracy.getOrElse(0) + 2 * kpi.accuracySTD)
        ) >= 3
      )
      .to(config.chessAnalyzer.topic.sink.suspiciousPlayer)

    builder.build()
  }

}
