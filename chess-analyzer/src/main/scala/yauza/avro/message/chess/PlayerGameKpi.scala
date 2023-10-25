/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class PlayerGameKpi(
    var id: String,
    var username: String,
    var gameId: String,
    var brilliantMoveCounter: Option[Long] = None,
    var excellentMoveCounter: Option[Long] = None,
    var goodMoveCounter: Option[Long] = None,
    var inaccuracyMoveCounter: Option[Long] = None,
    var mistakeMoveCounter: Option[Long] = None,
    var blunderMoveCounter: Option[Long] = None,
    var accuracy: Option[Double] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", None, None, None, None, None, None, None)
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
        {
          brilliantMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 4 =>
        {
          excellentMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 5 =>
        {
          goodMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 6 =>
        {
          inaccuracyMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 7 =>
        {
          mistakeMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 8 =>
        {
          blunderMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 9 =>
        {
          accuracy match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
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
        this.brilliantMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 4 =>
        this.excellentMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 5 =>
        this.goodMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 6 =>
        this.inaccuracyMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 7 =>
        this.mistakeMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 8 =>
        this.blunderMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 9 =>
        this.accuracy = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = PlayerGameKpi.SCHEMA$
}

object PlayerGameKpi {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"PlayerGameKpi\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gameId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"brilliantMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"excellentMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"goodMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"inaccuracyMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"mistakeMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"blunderMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"accuracy\",\"type\":[\"null\",\"double\"],\"default\":null}]}"
  )
}
