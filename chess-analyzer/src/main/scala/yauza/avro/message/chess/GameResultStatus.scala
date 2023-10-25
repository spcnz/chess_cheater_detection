/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameResultStatus(var id: Long, var name: String)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0L, "")
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        id
          .asInstanceOf[AnyRef]
      case 1 =>
        name
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.id = value
          .asInstanceOf[Long]
      case 1 =>
        this.name = value.toString
          .asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GameResultStatus.SCHEMA$
}

object GameResultStatus {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameResultStatus\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}"
  )
}
