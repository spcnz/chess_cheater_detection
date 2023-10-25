/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package yauza.avro.message.chess

import scala.annotation.switch

final case class GameClock(var initial: Int, var increment: Int, var totalTime: Int)
    extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0, 0, 0)
  def get(field$ : Int): AnyRef =
    (field$ : @switch) match {
      case 0 =>
        initial
          .asInstanceOf[AnyRef]
      case 1 =>
        increment
          .asInstanceOf[AnyRef]
      case 2 =>
        totalTime
          .asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  def put(field$ : Int, value: Any): Unit = {
    (field$ : @switch) match {
      case 0 =>
        this.initial = value
          .asInstanceOf[Int]
      case 1 =>
        this.increment = value
          .asInstanceOf[Int]
      case 2 =>
        this.totalTime = value
          .asInstanceOf[Int]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = GameClock.SCHEMA$
}

object GameClock {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
    "{\"type\":\"record\",\"name\":\"GameClock\",\"namespace\":\"yauza.avro.message.chess\",\"fields\":[{\"name\":\"initial\",\"type\":\"int\"},{\"name\":\"increment\",\"type\":\"int\"},{\"name\":\"totalTime\",\"type\":\"int\"}]}"
  )
}
