/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameUser(var name: String, var id: String)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "")
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        name
          .asInstanceOf[AnyRef]
      case 1 =>
        id
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.name = value.toString
          .asInstanceOf[String]
      case 1 =>
        this.id = value.toString
          .asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GameUser.SCHEMA$
}

object GameUser {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameUser\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}"
  )
}
