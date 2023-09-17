package yauza.chess

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.{KStream, KTable, Materialized}
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.state.Stores
import yauza.avro.message.chess._
import yauza.chess.engine.Engine
import yauza.chess.joiner.MoveGameJoiner
import yauza.chess.mapper.ChessAnalysisMapper
import yauza.chess.processor.GameScoreProcessor

case class TopologyBuilder(chessEngine: Engine)(implicit config: Config)
    extends Serdes
    with ChessAnalysisMapper
    with MoveGameJoiner
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

    val gameTable: KTable[String, Game] = builder
      .table(
        config.chessAnalyzer.topic.input.game,
        Materialized.as(config.chessAnalyzer.store.game)
      )

    val moveStream: KStream[String, MoveWithScore] =
      builder
        .stream[String, Move](config.chessAnalyzer.topic.input.move)
        .mapValues(move => mapMove(chessEngine)(move))
        .peek((k, v) =>
          logger.whenDebugEnabled {
            logger.debug(s"Enriched move {key: $k} with Stockfish score: $v")
          }
        )

    moveStream
      .join(gameTable)(joinMoveWithGame)
      .process[String, PlayerMove](
        () => GameScoreProcessor(config.chessAnalyzer.store.gameScore),
        config.chessAnalyzer.store.gameScore
      )
      .to("test-topic")

    builder.build()
  }
}
