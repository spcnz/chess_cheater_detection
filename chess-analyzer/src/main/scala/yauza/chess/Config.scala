package yauza.chess

import pureconfig._
import pureconfig.generic.auto._

case class SinkTopicConfig(
    playerGameKpi: String,
    playerKpi: String,
    playerMoveScore: String,
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
case class StoreConfig(
    game: String,
    gameScore: String,
    uniqueUpdates: String
)

case class EngineConfig(path: String)
case class ChessAnalyzer(topic: TopicConfig, store: StoreConfig, engine: EngineConfig)

case class Config(
    chessAnalyzer: ChessAnalyzer,
    kafka: KafkaConfig
) extends StreamConfig(kafka)

object Config {
  def apply(): Config = ConfigSource.default.loadOrThrow[Config]
}
