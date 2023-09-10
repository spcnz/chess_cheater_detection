package yauza.chess

import gaia.streams.common.StreamTopologyBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.kstream.KStream
import org.apache.kafka.streams.scala.serialization.Serdes._
import yauza.avro.message.chess.{Move, MoveWithScore}
import yauza.chess.engine.Engine
import yauza.chess.mapper.ChessAnalysisMapper

case class TopologyBuilder(chessEngine: Engine)(implicit config: Config)
    extends StreamTopologyBuilder
    with Serdes
    with ChessAnalysisMapper {

  override def build: Topology = {

    logger.info("Started topology moves")

    val moveStream: KStream[String, MoveWithScore] =
      builder
        .stream[String, Move](config.chessAnalyzer.topic.input.move)
        .mapValues(move => mapMove(chessEngine)(move))
        .peek((k, v) =>
          logger.whenDebugEnabled {
            logger.debug(s"Enriched move {key: $k} with Stockfish score: $v")
          }
        )

    moveStream.to("test-topic")

    builder.build()
  }
}
