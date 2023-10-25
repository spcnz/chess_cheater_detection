/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class Player(
    var id: String,
    var username: String,
    var perfs: perfs,
    var title: Option[String] = None,
    var createdAt: Long,
    var profile: Option[profile] = None,
    var seenAt: Long,
    var playTime: playTime,
    var url: String,
    var count: Option[count] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", new perfs, None, 0L, None, 0L, new playTime, "", None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        id
          .asInstanceOf[AnyRef]
      case 1 =>
        username
          .asInstanceOf[AnyRef]
      case 2 =>
        perfs
          .asInstanceOf[AnyRef]
      case 3 =>
        {
          title match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 4 =>
        createdAt
          .asInstanceOf[AnyRef]
      case 5 =>
        {
          profile match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 6 =>
        seenAt
          .asInstanceOf[AnyRef]
      case 7 =>
        playTime
          .asInstanceOf[AnyRef]
      case 8 =>
        url
          .asInstanceOf[AnyRef]
      case 9 =>
        {
          count match {
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
        this.perfs = value
          .asInstanceOf[perfs]
      case 3 =>
        this.title = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case 4 =>
        this.createdAt = value
          .asInstanceOf[Long]
      case 5 =>
        this.profile = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[profile]]
      case 6 =>
        this.seenAt = value
          .asInstanceOf[Long]
      case 7 =>
        this.playTime = value
          .asInstanceOf[playTime]
      case 8 =>
        this.url = value.toString
          .asInstanceOf[String]
      case 9 =>
        this.count = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[count]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = Player.SCHEMA$
}

object Player {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"Player\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"perfs\",\"type\":{\"type\":\"record\",\"name\":\"perfs\",\"fields\":[{\"name\":\"racer\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"PuzzleStatistic\",\"fields\":[{\"name\":\"runs\",\"type\":\"long\"},{\"name\":\"score\",\"type\":\"long\"}]}],\"default\":null},{\"name\":\"storm\",\"type\":[\"null\",\"PuzzleStatistic\"],\"default\":null},{\"name\":\"atomic\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"GameStatistic\",\"fields\":[{\"name\":\"games\",\"type\":\"long\"},{\"name\":\"rating\",\"type\":\"long\"},{\"name\":\"rd\",\"type\":\"long\"},{\"name\":\"prog\",\"type\":\"long\"},{\"name\":\"prov\",\"type\":[\"null\",\"boolean\"],\"default\":null}]}],\"default\":null},{\"name\":\"ultraBullet\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"blitz\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"crazyhouse\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"threeCheck\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"bullet\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"correspondence\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"horde\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"puzzle\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"classical\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null},{\"name\":\"rapid\",\"type\":[\"null\",\"GameStatistic\"],\"default\":null}]}},{\"name\":\"title\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"createdAt\",\"type\":\"long\"},{\"name\":\"profile\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"profile\",\"fields\":[{\"name\":\"country\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}],\"default\":null},{\"name\":\"seenAt\",\"type\":\"long\"},{\"name\":\"playTime\",\"type\":{\"type\":\"record\",\"name\":\"playTime\",\"fields\":[{\"name\":\"total\",\"type\":\"long\"},{\"name\":\"tv\",\"type\":\"long\"}]}},{\"name\":\"url\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"count\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"count\",\"fields\":[{\"name\":\"all\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"rated\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"ai\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"draw\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"drawH\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"loss\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"lossH\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"win\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"winH\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"bookmark\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"playing\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"import\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"me\",\"type\":[\"null\",\"long\"],\"default\":null}]}],\"default\":null}]}"
  )
}
