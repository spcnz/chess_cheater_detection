package yauza.chess

import gaia.streams.common.AvroSupport
import org.apache.kafka.common.serialization.Serde
import yauza.avro.message.chess._

trait Serdes extends AvroSupport {
  implicit val gameSerde: Serde[Game] = avroSerde()
  implicit val playerSerde: Serde[Player] = avroSerde()
  implicit val moveSerde: Serde[Move] = avroSerde()

  implicit val moveWithScoreSerde: Serde[MoveWithScore] = avroSerde()
  implicit val gameWithMoveScoreSerde: Serde[GameWithMoveScore] = avroSerde()
  implicit val gameScoreSerde: Serde[GameScore] = avroSerde()
  implicit val playerMoveSerde: Serde[PlayerMove] = avroSerde()

  implicit val playerGameKpiSerde: Serde[PlayerGameKpi] = avroSerde()
}
