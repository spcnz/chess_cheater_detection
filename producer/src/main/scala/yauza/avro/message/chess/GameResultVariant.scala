/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameResultVariant(var key: String, var name: String, var short : String)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "")
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        key
          .asInstanceOf[AnyRef]
      case 1 =>
        name
          .asInstanceOf[AnyRef]
      case 2 =>
        short
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.key = value.toString
          .asInstanceOf[String]
      case 1 =>
        this.name = value.toString
          .asInstanceOf[String]
      case 2 =>
        this.short = value.toString
          .asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GameResultVariant.SCHEMA$
}

object GameResultVariant {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameResultVariant\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"key\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"short\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}"
  )
}
