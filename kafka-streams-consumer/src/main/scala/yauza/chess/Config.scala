package yauza.chess

import gaia.streams.common.{KafkaConfig, StreamConfig}
import pureconfig._
import pureconfig.generic.auto._

case class SinkTopicConfig(
    playerKpi: String,
    suspiciousPlayer: String
)

case class InputTopicConfig(
    game: String,
    move: String,
    gameResult: String,
    player: String
)

case class TopicConfig(
    input: InputTopicConfig,
    sink: SinkTopicConfig
)

case class EngineConfig(path: String)
case class ChessAnalyzer(topic: TopicConfig, engine: EngineConfig)

case class Config(
    chessAnalyzer: ChessAnalyzer,
    kafka: KafkaConfig
) extends StreamConfig(kafka)

object Config {
  def apply(): Config = ConfigSource.default.loadOrThrow[Config]
}
