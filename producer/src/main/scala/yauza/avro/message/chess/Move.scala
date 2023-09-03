/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class Move(
    var gameId: Option[String] = None,
    var fen: String,
    var lm: String,
    var wc: Long,
    var bc: Long,
    var moveIndex: Option[Long]
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(None, "", "", 0L, 0L, None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        {
          gameId match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 1 =>
        fen
          .asInstanceOf[AnyRef]
      case 2 =>
        lm
          .asInstanceOf[AnyRef]
      case 3 =>
        wc
          .asInstanceOf[AnyRef]
      case 4 =>
        bc
          .asInstanceOf[AnyRef]
      case 5 =>
        {
          moveIndex match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.gameId = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case 1 =>
        this.fen = value.toString
          .asInstanceOf[String]
      case 2 =>
        this.lm = value.toString
          .asInstanceOf[String]
      case 3 =>
        this.wc = value
          .asInstanceOf[Long]
      case 4 =>
        this.bc = value
          .asInstanceOf[Long]
      case 5 =>
        this.moveIndex = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = Move.SCHEMA$
}

object Move {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"Move\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"gameId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"fen\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"lm\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"wc\",\"type\":\"long\"},{\"name\":\"bc\",\"type\":\"long\"},{\"name\":\"moveIndex\",\"type\":[\"null\",\"long\"]}]}"
  )
}
