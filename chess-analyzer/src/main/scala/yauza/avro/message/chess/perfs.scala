/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class perfs(
    var racer: Option[PuzzleStatistic] = None,
    var storm: Option[PuzzleStatistic] = None,
    var atomic: Option[GameStatistic] = None,
    var ultraBullet: Option[GameStatistic] = None,
    var blitz: Option[GameStatistic] = None,
    var crazyhouse: Option[GameStatistic] = None,
    var threeCheck: Option[GameStatistic] = None,
    var bullet: Option[GameStatistic] = None,
    var correspondence: Option[GameStatistic] = None,
    var horde: Option[GameStatistic] = None,
    var puzzle: Option[GameStatistic] = None,
    var classical: Option[GameStatistic] = None,
    var rapid: Option[GameStatistic] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(None, None, None, None, None, None, None, None, None, None, None, None, None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        {
          racer match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 1 =>
        {
          storm match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 2 =>
        {
          atomic match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 3 =>
        {
          ultraBullet match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 4 =>
        {
          blitz match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 5 =>
        {
          crazyhouse match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 6 =>
        {
          threeCheck match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 7 =>
        {
          bullet match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 8 =>
        {
          correspondence match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 9 =>
        {
          horde match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 10 =>
        {
          puzzle match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 11 =>
        {
          classical match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 12 =>
        {
          rapid match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.racer = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[PuzzleStatistic]]
      case 1 =>
        this.storm = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[PuzzleStatistic]]
      case 2 =>
        this.atomic = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 3 =>
        this.ultraBullet = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 4 =>
        this.blitz = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 5 =>
        this.crazyhouse = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 6 =>
        this.threeCheck = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 7 =>
        this.bullet = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 8 =>
        this.correspondence = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 9 =>
        this.horde = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 10 =>
        this.puzzle = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 11 =>
        this.classical = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case 12 =>
        this.rapid = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = perfs.SCHEMA$
}

object perfs {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"perfs\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"racer\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"PuzzleStatistic\",\"fields\":[{\"name\":\"runs\",\"type\":\"long\"},{\"name\":\"score\",\"type\":\"long\"}]}],\"default\":null},{\"name\":\"storm\",\"type\":[\"null\",\"PuzzleStatistic\"],\"default\":null},{\"name\":\"atomic\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"GameStatistic\",\"fields\":[{\"name\":\"games\",\"type\":\"long\"},{\"name\":\"rating\",\"type\":\"long\"},{\"name\":\"rd\",\"type\":\"long\"},{\"name\":\"prog\",\"type\":\"long\"},{\"name\":\"prov\",\"type\":[\"null\",\"boolean\"],\"default\":null}]}],\"default\":null},{\"name\":\"ultraBullet\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"blitz\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"crazyhouse\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"threeCheck\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"bullet\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"correspondence\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"horde\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"puzzle\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"classical\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"rapid\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null}]}"
  )
}
