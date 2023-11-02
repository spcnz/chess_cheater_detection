package yauza.chess

import com.typesafe.config.{Config, ConfigObject}
import org.apache.kafka.streams.StreamsConfig

import java.util.Properties
import scala.reflect.io.Path

case class KafkaConfig(
    consumer: ConfigObject,
    producer: ConfigObject,
    topic: ConfigObject,
    streams: ConfigObject
)

class StreamConfig(kafka: KafkaConfig) {
  protected val streamConfig: Config = kafka.streams.toConfig
  protected val consumerConfig: Config = kafka.consumer.toConfig
  protected val producerConfig: Config = kafka.producer.toConfig
  protected val topicConfig: Config = kafka.topic.toConfig

  def applicationId: String = streamConfig.getString("application.id")
  def schemaRegistryUrl: String = streamConfig.getString("schema.registry.url")
  def stateDir: Path = Path(streamConfig.getString("state.dir"))

  def streamProperties: Properties = {
    val props = new Properties()

    streamConfig.entrySet.forEach { entry =>
      props.put(
        entry.getKey,
        entry.getValue.unwrapped
      )
    }

    consumerConfig.entrySet.forEach { entry =>
      props.put(
        StreamsConfig.consumerPrefix(entry.getKey),
        entry.getValue.unwrapped
      )
    }

    producerConfig.entrySet.forEach { entry =>
      props.put(
        StreamsConfig.producerPrefix(entry.getKey),
        entry.getValue.unwrapped
      )
    }

    topicConfig.entrySet.forEach { entry =>
      props.put(
        StreamsConfig.topicPrefix(entry.getKey),
        entry.getValue.unwrapped
      )
    }

    props
  }

}
