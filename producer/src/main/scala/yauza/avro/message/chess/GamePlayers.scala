/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GamePlayers(var white: GamePlayer, var black: GamePlayer)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(new GamePlayer, new GamePlayer)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        white
          .asInstanceOf[AnyRef]
      case 1 =>
        black
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.white = value
          .asInstanceOf[GamePlayer]
      case 1 =>
        this.black = value
          .asInstanceOf[GamePlayer]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GamePlayers.SCHEMA$
}

object GamePlayers {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GamePlayers\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"white\",\"type\":{\"type\":\"record\",\"name\":\"GamePlayer\",\"fields\":[{\"name\":\"user\",\"type\":{\"type\":\"record\",\"name\":\"GameUser\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"rating\",\"type\":\"int\"},{\"name\":\"ratingDiff\",\"type\":[\"null\",\"int\"]}]}},{\"name\":\"black\",\"type\":\"GamePlayer\"}]}"
  )
}
