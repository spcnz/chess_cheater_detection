/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameKpi(
    var id: String,
    var username: String,
    var gameId: String,
    var gameStatus: String = "started",
    var rated: Option[Boolean] = None,
    var winner: Option[String] = None,
    var brilliantMoveCounter: Option[Long] = None,
    var excellentMoveCounter: Option[Long] = None,
    var goodMoveCounter: Option[Long] = None,
    var inaccuracyMoveCounter: Option[Long] = None,
    var mistakeMoveCounter: Option[Long] = None,
    var blunderMoveCounter: Option[Long] = None,
    var accuracy: Option[Double] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "started", None, None, None, None, None, None, None, None, None)
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
        gameStatus
          .asInstanceOf[AnyRef]
      case 4 =>
        {
          rated match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 5 =>
        {
          winner match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 6 =>
        {
          brilliantMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 7 =>
        {
          excellentMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 8 =>
        {
          goodMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 9 =>
        {
          inaccuracyMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 10 =>
        {
          mistakeMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 11 =>
        {
          blunderMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 12 =>
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
        this.gameStatus = value.toString
          .asInstanceOf[String]
      case 4 =>
        this.rated = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Boolean]]
      case 5 =>
        this.winner = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case 6 =>
        this.brilliantMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 7 =>
        this.excellentMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 8 =>
        this.goodMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 9 =>
        this.inaccuracyMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 10 =>
        this.mistakeMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 11 =>
        this.blunderMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 12 =>
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
  def getSchema: org.apache.avro.Schema = GameKpi.SCHEMA$
}

object GameKpi {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameKpi\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gameId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gameStatus\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"started\"},{\"name\":\"rated\",\"type\":[\"null\",\"boolean\"],\"default\":null},{\"name\":\"winner\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"brilliantMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"excellentMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"goodMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"inaccuracyMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"mistakeMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"blunderMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"accuracy\",\"type\":[\"null\",\"double\"],\"default\":null}]}"
  )
}
