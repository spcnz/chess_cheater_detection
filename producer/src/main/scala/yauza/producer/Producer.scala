package yauza.producer

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import org.apache.avro.specific.SpecificRecordBase
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import yauza.avro.message.chess.Move
import yauza.config.Configuration

import java.util.Properties

trait Producer {

  def props(implicit config: Configuration): Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafka.bootstrapServers)
    props.put(ProducerConfig.ACKS_CONFIG, config.kafka.acks)
    props.put(
      AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
      config.kafka.schemaRegistryUrl
    )
    props.put(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    props.put(
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "io.confluent.kafka.serializers.KafkaAvroSerializer"
    )

    props
  }

  def produce(): Unit
}
