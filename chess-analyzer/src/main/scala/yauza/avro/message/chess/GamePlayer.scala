/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GamePlayer(var user: GameUser, var rating: Int, var ratingDiff: Option[Int])
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(new GameUser, 0, None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        user
          .asInstanceOf[AnyRef]
      case 1 =>
        rating
          .asInstanceOf[AnyRef]
      case 2 =>
        {
          ratingDiff match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.user = value
          .asInstanceOf[GameUser]
      case 1 =>
        this.rating = value
          .asInstanceOf[Int]
      case 2 =>
        this.ratingDiff = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Int]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GamePlayer.SCHEMA$
}

object GamePlayer {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GamePlayer\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"user\",\"type\":{\"type\":\"record\",\"name\":\"GameUser\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"rating\",\"type\":\"int\"},{\"name\":\"ratingDiff\",\"type\":[\"null\",\"int\"]}]}"
  )
}
