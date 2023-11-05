/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class count(
    var all: Option[Long] = None,
    var rated: Option[Long] = None,
    var draw: Option[Long] = None,
    var loss: Option[Long] = None,
    var win: Option[Long] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(None, None, None, None, None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        {
          all match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 1 =>
        {
          rated match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 2 =>
        {
          draw match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 3 =>
        {
          loss match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 4 =>
        {
          win match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.all = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 1 =>
        this.rated = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 2 =>
        this.draw = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 3 =>
        this.loss = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 4 =>
        this.win = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = count.SCHEMA$
}

object count {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"count\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"all\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"rated\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"draw\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"loss\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"win\",\"type\":[\"null\",\"long\"],\"default\":null}]}"
  )
}
