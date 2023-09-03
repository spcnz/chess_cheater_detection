package yauza.config

import pureconfig._
import pureconfig.generic.auto._

case class Configuration(api: ApiConfig, kafka: KafkaConfig, app: ProducerConfig)

case class ApiConfig(
    root: String,
    numGames: Int,
    games: String,
    moves: String,
    gameResults: String,
    player: String
)
case class KafkaConfig(
    bootstrapServers: String,
    schemaRegistryUrl: String,
    batchSize: Int,
    acks: String,
    topics: KafkaSinkTopics
)
case class KafkaSinkTopics(
    games: String,
    moves: String,
    gameResults: String,
    players: String
)

case class ProducerConfig(sleepTimeMs: Int)

object Configuration {
  def apply(): Configuration = ConfigSource.default.loadOrThrow[Configuration]
}
