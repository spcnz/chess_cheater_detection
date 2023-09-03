/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class profile(var country: Option[String] = None)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        {
          country match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.country = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = profile.SCHEMA$
}

object profile {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"profile\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"country\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}"
  )
}
