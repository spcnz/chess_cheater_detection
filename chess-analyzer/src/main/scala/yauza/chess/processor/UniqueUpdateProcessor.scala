package yauza.chess.processor

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.common.utils.Utils
import org.apache.kafka.streams.processor.api.{Processor, ProcessorContext, Record}
import org.apache.kafka.streams.state.KeyValueStore

import scala.reflect.runtime.universe._

case class UniqueUpdateProcessor[K: TypeTag, T: TypeTag](
    stateStore: String,
    hash: T => Int = (value: T) => Utils.murmur2(value.toString.getBytes)
) extends Processor[K, T, K, T]
    with LazyLogging {

  private var uniqueUpdatesStore: KeyValueStore[String, Int] = _
  private var context: ProcessorContext[K, T] = _

  override def init(processorContext: ProcessorContext[K, T]): Unit = {
    context = processorContext
    uniqueUpdatesStore = processorContext
      .getStateStore(stateStore)
      .asInstanceOf[KeyValueStore[String, Int]]
  }

  override def process(record: Record[K, T]): Unit = {
    val (key, value) = (record.key(), record.value())
    val valueType = typeOf[T].toString.split('.').last
    val storeKey = s"$valueType:$key"
    val valueHash = hash(value)

    Option(uniqueUpdatesStore.get(storeKey)) match {
      case Some(hash) =>
        if (hash != valueHash) {
          logger.info(s"Hashes don't match for $storeKey. Updating.")
          uniqueUpdatesStore.put(storeKey, valueHash)
          context.forward(record.withKey(key).withValue(value))
        }
      case _ =>
        logger.info(s"No hash found for $storeKey. Saving new hash.")
        uniqueUpdatesStore.put(storeKey, valueHash)
        context.forward(record.withKey(key).withValue(value))
    }
  }

  override def close(): Unit = {}

}
