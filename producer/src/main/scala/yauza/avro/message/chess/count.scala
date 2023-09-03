/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class count(
    var all: Option[Long] = None,
    var rated: Option[Long] = None,
    var ai: Option[Long] = None,
    var draw: Option[Long] = None,
    var drawH: Option[Long] = None,
    var loss: Option[Long] = None,
    var lossH: Option[Long] = None,
    var win: Option[Long] = None,
    var winH: Option[Long] = None,
    var bookmark: Option[Long] = None,
    var playing: Option[Long] = None,
    var import$ : Option[Long] = None,
    var me: Option[Long] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(None, None, None, None, None, None, None, None, None, None, None, None, None)
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
          ai match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 3 =>
        {
          draw match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 4 =>
        {
          drawH match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 5 =>
        {
          loss match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 6 =>
        {
          lossH match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 7 =>
        {
          win match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 8 =>
        {
          winH match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 9 =>
        {
          bookmark match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 10 =>
        {
          playing match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 11 =>
        {
          import$ match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 12 =>
        {
          me match {
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
        this.ai = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 3 =>
        this.draw = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 4 =>
        this.drawH = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 5 =>
        this.loss = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 6 =>
        this.lossH = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 7 =>
        this.win = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 8 =>
        this.winH = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 9 =>
        this.bookmark = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 10 =>
        this.playing = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 11 =>
        this.import$ = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 12 =>
        this.me = {
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
    "{\"type\":\"record\",\"name\":\"count\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"all\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"rated\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"ai\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"draw\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"drawH\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"loss\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"lossH\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"win\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"winH\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"bookmark\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"playing\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"import\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"me\",\"type\":[\"null\",\"long\"],\"default\":null}]}"
  )
}
