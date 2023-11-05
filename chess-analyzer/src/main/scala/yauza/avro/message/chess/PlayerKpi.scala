/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class PlayerKpi(
    var id: String,
    var username: String,
    var bulletGameStatistic: Option[GameStatistic] = None,
    var title: Option[String] = None,
    var createdAt: Long,
    var country: Option[String] = None,
    var seenAt: Long,
    var playTime: Option[Long],
    var url: String,
    var gameCount: Option[count] = None,
    var incorrectMoveCounter: Option[Long] = None,
    var correctMoveCounter: Option[Long] = None,
    var correctIncorrectMoveRatio: Option[Double] = None,
    var winLossRatio: Option[Double] = None,
    var meanPlayerAccuracy: Option[Double] = None,
    var macroAccuracy: Option[Double] = None,
    var accuracies: Map[String, Double] = Map(),
    var accuracySTD: Option[Double] = None,
    var accuracyMedian: Option[Double] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(
    "",
    "",
    None,
    None,
    0L,
    None,
    0L,
    None,
    "",
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    Map(),
    None,
    None
  )
  def get(field$ : Int): AnyRef = {
    (field$ : @switch) match {
      case 0 =>
        id
          .asInstanceOf[AnyRef]
      case 1 =>
        username
          .asInstanceOf[AnyRef]
      case 2 =>
        {
          bulletGameStatistic match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
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
          country match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 6 =>
        seenAt
          .asInstanceOf[AnyRef]
      case 7 =>
        {
          playTime match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 8 =>
        url
          .asInstanceOf[AnyRef]
      case 9 =>
        {
          gameCount match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 10 =>
        {
          incorrectMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 11 =>
        {
          correctMoveCounter match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 12 =>
        {
          correctIncorrectMoveRatio match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 13 =>
        {
          winLossRatio match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 14 =>
        {
          meanPlayerAccuracy match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 15 =>
        {
          macroAccuracy match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 16 =>
        {
          val map: java.util.HashMap[String, Any] = new java.util.HashMap[String, Any]
          accuracies foreach { kvp =>
            val key = kvp._1
            val value = kvp._2
            map.put(key, value)
          }
          map
        }.asInstanceOf[AnyRef]
      case 17 =>
        {
          accuracySTD match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case 18 =>
        {
          accuracyMedian match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
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
        this.bulletGameStatistic = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[GameStatistic]]
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
        this.country = {
          value match {
            case null => None
            case _    => Some(value.toString)
          }
        }.asInstanceOf[Option[String]]
      case 6 =>
        this.seenAt = value
          .asInstanceOf[Long]
      case 7 =>
        this.playTime = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 8 =>
        this.url = value.toString
          .asInstanceOf[String]
      case 9 =>
        this.gameCount = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[count]]
      case 10 =>
        this.incorrectMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 11 =>
        this.correctMoveCounter = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Long]]
      case 12 =>
        this.correctIncorrectMoveRatio = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case 13 =>
        this.winLossRatio = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case 14 =>
        this.meanPlayerAccuracy = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case 15 =>
        this.macroAccuracy = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case 16 =>
        this.accuracies = {
          value match {
            case (map: java.util.Map[_, _]) =>
              scala.collection.JavaConverters.mapAsScalaMapConverter(map).asScala.toMap map { kvp =>
                val key = kvp._1.toString
                val value = kvp._2
                (key, value)
              }
          }
        }.asInstanceOf[Map[String, Double]]
      case 17 =>
        this.accuracySTD = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case 18 =>
        this.accuracyMedian = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Double]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = PlayerKpi.SCHEMA$
}

object PlayerKpi {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"PlayerKpi\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"bulletGameStatistic\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"GameStatistic\",\"fields\":[{\"name\":\"games\",\"type\":\"long\"},{\"name\":\"rating\",\"type\":\"long\"},{\"name\":\"rd\",\"type\":\"long\"},{\"name\":\"prog\",\"type\":\"long\"},{\"name\":\"prov\",\"type\":[\"null\",\"boolean\"],\"default\":null}]}],\"default\":null},{\"name\":\"title\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"createdAt\",\"type\":\"long\"},{\"name\":\"country\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"seenAt\",\"type\":\"long\"},{\"name\":\"playTime\",\"type\":[\"null\",\"long\"]},{\"name\":\"url\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gameCount\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"count\",\"fields\":[{\"name\":\"all\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"rated\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"draw\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"loss\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"win\",\"type\":[\"null\",\"long\"],\"default\":null}]}],\"default\":null},{\"name\":\"incorrectMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"correctMoveCounter\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"correctIncorrectMoveRatio\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"winLossRatio\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"meanPlayerAccuracy\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"macroAccuracy\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"accuracies\",\"type\":{\"type\":\"map\",\"values\":\"double\",\"keys\":\"string\"},\"default\":{}},{\"name\":\"accuracySTD\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"accuracyMedian\",\"type\":[\"null\",\"double\"],\"default\":null}]}"
  )
}
