/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameStatistic(
    var games: Long,
    var rating: Long,
    var rd: Long,
    var prog: Long,
    var prov: Option[Boolean] = None
) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0L, 0L, 0L, 0L, None)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        games
          .asInstanceOf[AnyRef]
      case 1 =>
        rating
          .asInstanceOf[AnyRef]
      case 2 =>
        rd
          .asInstanceOf[AnyRef]
      case 3 =>
        prog
          .asInstanceOf[AnyRef]
      case 4 =>
        {
          prov match {
            case Some(x) => x
            case None    => null
          }
        }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.games = value
          .asInstanceOf[Long]
      case 1 =>
        this.rating = value
          .asInstanceOf[Long]
      case 2 =>
        this.rd = value
          .asInstanceOf[Long]
      case 3 =>
        this.prog = value
          .asInstanceOf[Long]
      case 4 =>
        this.prov = {
          value match {
            case null => None
            case _    => Some(value)
          }
        }.asInstanceOf[Option[Boolean]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GameStatistic.SCHEMA$
}

object GameStatistic {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameStatistic\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"games\",\"type\":\"long\"},{\"name\":\"rating\",\"type\":\"long\"},{\"name\":\"rd\",\"type\":\"long\"},{\"name\":\"prog\",\"type\":\"long\"},{\"name\":\"prov\",\"type\":[\"null\",\"boolean\"],\"default\":null}]}"
  )
}
