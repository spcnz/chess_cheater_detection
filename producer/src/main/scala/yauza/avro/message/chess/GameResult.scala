/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameResult(
    var id: String,
    var variant: GameResultVariant,
    var speed: String,
    var perf: String,
    var rated: Boolean,
    var fen: String,
    var player: String,
    var turns: Long,
    var startedAtTurn: Option[Long],
    var source: String,
    var status: GameResultStatus,
    var createdAt: Long,
    var winner: String,
    var lastMove: String,
    var check: Option[String],
    var rematch: Option[String]
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(
    "",
    new GameResultVariant,
    "",
    "",
    false,
    "",
    "",
    0L,
    None,
    "",
    new GameResultStatus,
    0L,
    "",
    "",
    None,
    None
  )
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        id
          .asInstanceOf[AnyRef]
      case 1 =>
        variant
          .asInstanceOf[AnyRef]
      case 2 =>
        speed
          .asInstanceOf[AnyRef]
      case 3 =>
        perf
          .asInstanceOf[AnyRef]
      case 4 =>
        rated
          .asInstanceOf[AnyRef]
      case 5 =>
        fen
          .asInstanceOf[AnyRef]
      case 6 =>
        player
          .asInstanceOf[AnyRef]
      case 7 =>
        turns
          .asInstanceOf[AnyRef]
      case 8 =>
        {
          startedAtTurn match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 9 =>
        source
          .asInstanceOf[AnyRef]
      case 10 =>
        status
          .asInstanceOf[AnyRef]
      case 11 =>
        createdAt
          .asInstanceOf[AnyRef]
      case 12 =>
        winner
          .asInstanceOf[AnyRef]
      case 13 =>
        lastMove
          .asInstanceOf[AnyRef]
      case 14 =>
        {
          check match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 15 =>
        {
          rematch match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.id = value.toString
          .asInstanceOf[String]
      case 1 =>
        this.variant = value
          .asInstanceOf[GameResultVariant]
      case 2 =>
        this.speed = value.toString
          .asInstanceOf[String]
      case 3 =>
        this.perf = value.toString
          .asInstanceOf[String]
      case 4 =>
        this.rated = value
          .asInstanceOf[Boolean]
      case 5 =>
        this.fen = value.toString
          .asInstanceOf[String]
      case 6 =>
        this.player = value.toString
          .asInstanceOf[String]
      case 7 =>
        this.turns = value
          .asInstanceOf[Long]
      case 8 =>
        this.startedAtTurn = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 9 =>
        this.source = value.toString
          .asInstanceOf[String]
      case 10 =>
        this.status = value
          .asInstanceOf[GameResultStatus]
      case 11 =>
        this.createdAt = value
          .asInstanceOf[Long]
      case 12 =>
        this.winner = value.toString
          .asInstanceOf[String]
      case 13 =>
        this.lastMove = value.toString
          .asInstanceOf[String]
      case 14 =>
        this.check = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case 15 =>
        this.rematch = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GameResult.SCHEMA$
}

object GameResult {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameResult\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"variant\",\"type\":{\"type\":\"record\",\"name\":\"GameResultVariant\",\"fields\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"short\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"speed\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"perf\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"rated\",\"type\":\"boolean\"},{\"name\":\"fen\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"player\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"turns\",\"type\":\"long\"},{\"name\":\"startedAtTurn\",\"type\":[\"null\",\"long\"]},{\"name\":\"source\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"status\",\"type\":{\"type\":\"record\",\"name\":\"GameResultStatus\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"createdAt\",\"type\":\"long\"},{\"name\":\"winner\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"lastMove\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"check\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},{\"name\":\"rematch\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]}]}"
  )
}
