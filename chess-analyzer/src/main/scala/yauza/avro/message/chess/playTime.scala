/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class playTime(var total: Long, var tv: Long)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0L, 0L)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        total
          .asInstanceOf[AnyRef]
      case 1 =>
        tv
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.total = value
          .asInstanceOf[Long]
      case 1 =>
        this.tv = value
          .asInstanceOf[Long]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = playTime.SCHEMA$
}

object playTime {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"playTime\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"total\",\"type\":\"long\"},{\"name\":\"tv\",\"type\":\"long\"}]}"
  )
}
