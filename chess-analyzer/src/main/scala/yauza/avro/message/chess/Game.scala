/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class Game(
    var id: String,
    var rated: Boolean,
    var variant: String,
    var speed: String,
    var perf: String,
    var createdAt: Long,
    var lastMoveAt: Long,
    var status: String,
    var players: GamePlayers,
    var moves: String,
    var clock: GameClock
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", false, "", "", "", 0L, 0L, "", new GamePlayers, "", new GameClock)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        id
          .asInstanceOf[AnyRef]
      case 1 =>
        rated
          .asInstanceOf[AnyRef]
      case 2 =>
        variant
          .asInstanceOf[AnyRef]
      case 3 =>
        speed
          .asInstanceOf[AnyRef]
      case 4 =>
        perf
          .asInstanceOf[AnyRef]
      case 5 =>
        createdAt
          .asInstanceOf[AnyRef]
      case 6 =>
        lastMoveAt
          .asInstanceOf[AnyRef]
      case 7 =>
        status
          .asInstanceOf[AnyRef]
      case 8 =>
        players
          .asInstanceOf[AnyRef]
      case 9 =>
        moves
          .asInstanceOf[AnyRef]
      case 10 =>
        clock
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.id = value.toString
          .asInstanceOf[String]
      case 1 =>
        this.rated = value
          .asInstanceOf[Boolean]
      case 2 =>
        this.variant = value.toString
          .asInstanceOf[String]
      case 3 =>
        this.speed = value.toString
          .asInstanceOf[String]
      case 4 =>
        this.perf = value.toString
          .asInstanceOf[String]
      case 5 =>
        this.createdAt = value
          .asInstanceOf[Long]
      case 6 =>
        this.lastMoveAt = value
          .asInstanceOf[Long]
      case 7 =>
        this.status = value.toString
          .asInstanceOf[String]
      case 8 =>
        this.players = value
          .asInstanceOf[GamePlayers]
      case 9 =>
        this.moves = value.toString
          .asInstanceOf[String]
      case 10 =>
        this.clock = value
          .asInstanceOf[GameClock]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = Game.SCHEMA$
}

object Game {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"Game\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"rated\",\"type\":\"boolean\"},{\"name\":\"variant\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"speed\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"perf\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"createdAt\",\"type\":\"long\"},{\"name\":\"lastMoveAt\",\"type\":\"long\"},{\"name\":\"status\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"players\",\"type\":{\"type\":\"record\",\"name\":\"GamePlayers\",\"fields\":[{\"name\":\"white\",\"type\":{\"type\":\"record\",\"name\":\"GamePlayer\",\"fields\":[{\"name\":\"user\",\"type\":{\"type\":\"record\",\"name\":\"GameUser\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"rating\",\"type\":\"int\"},{\"name\":\"ratingDiff\",\"type\":[\"null\",\"int\"]}]}},{\"name\":\"black\",\"type\":\"GamePlayer\"}]}},{\"name\":\"moves\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"clock\",\"type\":{\"type\":\"record\",\"name\":\"GameClock\",\"fields\":[{\"name\":\"initial\",\"type\":\"int\"},{\"name\":\"increment\",\"type\":\"int\"},{\"name\":\"totalTime\",\"type\":\"int\"}]}}]}"
  )
}
