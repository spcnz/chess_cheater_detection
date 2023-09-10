package yauza.chess

import gaia.streams.common.AvroSupport
import org.apache.kafka.common.serialization.Serde
import yauza.avro.message.chess.{Game, Move, Player}
import yauza.avro.message.chess.MoveWithScore

trait Serdes extends AvroSupport {
  implicit val gameSerde: Serde[Game] = avroSerde()
  implicit val playerSerde: Serde[Player] = avroSerde()
  implicit val moveSerde: Serde[Move] = avroSerde()
  implicit val moveWithScoreSerde: Serde[MoveWithScore] = avroSerde()
}
