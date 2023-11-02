package yauza.chess.util

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Serde
import yauza.chess.StreamConfig

import scala.jdk.CollectionConverters._

trait AvroSupport {

  def avroSerde[T <: SpecificRecord](
      isKey: Boolean = false
  )(implicit config: StreamConfig): Serde[T] = {
    val serde = new SpecificAvroSerde[T]
    val serdeConfig = Map(SCHEMA_REGISTRY_URL_CONFIG -> config.schemaRegistryUrl)
    serde.configure(serdeConfig.asJava, isKey)
    serde
  }

}
