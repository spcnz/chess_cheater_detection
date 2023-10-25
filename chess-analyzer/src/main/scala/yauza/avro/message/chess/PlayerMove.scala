/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class PlayerMove(
    var id: String,
    var username: String,
    var gameId: String,
    var lastMove: String,
    var label: MoveLabel,
    var score: Long = 0L
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", null, 0L)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        id
          .asInstanceOf[AnyRef]
      case 1 =>
        username
          .asInstanceOf[AnyRef]
      case 2 =>
        gameId
          .asInstanceOf[AnyRef]
      case 3 =>
        lastMove
          .asInstanceOf[AnyRef]
      case 4 =>
        label
          .asInstanceOf[AnyRef]
      case 5 =>
        score
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.id = value.toString
          .asInstanceOf[String]
      case 1 =>
        this.username = value.toString
          .asInstanceOf[String]
      case 2 =>
        this.gameId = value.toString
          .asInstanceOf[String]
      case 3 =>
        this.lastMove = value.toString
          .asInstanceOf[String]
      case 4 =>
        this.label = value
          .asInstanceOf[MoveLabel]
      case 5 =>
        this.score = value
          .asInstanceOf[Long]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = PlayerMove.SCHEMA$
}

object PlayerMove {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"PlayerMove\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gameId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"lastMove\",\"type\":\"string\"},{\"name\":\"label\",\"type\":{\"type\":\"enum\",\"name\":\"MoveLabel\",\"symbols\":[\"Blunder\",\"Mistake\",\"Inaccuracy\",\"Neutral\",\"Good\",\"Excellent\",\"Brilliant\"]}},{\"name\":\"score\",\"type\":\"long\",\"default\":0}]}"
  )
}
