package yauza.chess

import org.apache.kafka.common.serialization.Serde
import yauza.avro.message.chess._
import yauza.chess.util.AvroSupport

trait Serdes extends AvroSupport {
  implicit val gameSerde: Serde[Game] = avroSerde()
  implicit val gameResult: Serde[GameResult] = avroSerde()
  implicit val playerSerde: Serde[Player] = avroSerde()
  implicit val moveSerde: Serde[Move] = avroSerde()

  implicit val moveWithScoreSerde: Serde[MoveWithScore] = avroSerde()
  implicit val gameWithMoveScoreSerde: Serde[GameWithMoveScore] = avroSerde()
  implicit val gameScoreSerde: Serde[GameScore] = avroSerde()
  implicit val playerMoveSerde: Serde[PlayerMove] = avroSerde()

  implicit val gameKpiSerde: Serde[GameKpi] = avroSerde()
  implicit val playerKpiSerde: Serde[PlayerKpi] = avroSerde()
}
