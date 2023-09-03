package yauza.producer

import com.typesafe.scalalogging.LazyLogging
import yauza.config.Configuration
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import yauza.api.Api
import yauza.avro.message.chess.Game
import yauza.decoder.Decoder

case class GameProducer(api: Api)(implicit config: Configuration)
    extends Producer
    with Decoder
    with LazyLogging {

  val producer = new KafkaProducer[String, Game](super.props)

  override def produce(): Unit =
    while (true) {
      api.getGames.foreach(sendGame)
      Thread.sleep(config.app.sleepTimeMs)
    }

  def sendGame(game: Game): Unit = {
    producer.send(
      new ProducerRecord(
        config.kafka.topics.games,
        game.id,
        game
      )
    )
    producer.flush()
    logger.info(s"Produced message: ($game.id, ${game.variant})")
  }
}
