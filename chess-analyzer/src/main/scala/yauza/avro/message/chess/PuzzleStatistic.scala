/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class PuzzleStatistic(var runs: Long, var score: Long)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0L, 0L)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        runs
          .asInstanceOf[AnyRef]
      case 1 =>
        score
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.runs = value
          .asInstanceOf[Long]
      case 1 =>
        this.score = value
          .asInstanceOf[Long]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = PuzzleStatistic.SCHEMA$
}

object PuzzleStatistic {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"PuzzleStatistic\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"runs\",\"type\":\"long\"},{\"name\":\"score\",\"type\":\"long\"}]}"
  )
}
