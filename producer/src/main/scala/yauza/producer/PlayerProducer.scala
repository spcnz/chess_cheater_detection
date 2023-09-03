package yauza.producer

import com.typesafe.scalalogging.LazyLogging
import yauza.config.Configuration
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import yauza.api.Api
import yauza.avro.message.chess.Player
import yauza.decoder.Decoder

case class PlayerProducer(api: Api)(implicit config: Configuration)
    extends Producer
    with Decoder
    with LazyLogging {

  val producer = new KafkaProducer[String, Player](super.props)

  override def produce(): Unit =
    while (true) {
      api.getGames.foreach { game =>
        streamPlayers(game.players.white.user.id)
        streamPlayers(game.players.black.user.id)
      }
      Thread.sleep(config.app.sleepTimeMs)
    }

  def streamPlayers(id: String): Unit = {
    val player = api.getPlayer(id)
    producer.send(
      new ProducerRecord(
        config.kafka.topics.players,
        player.id,
        player
      )
    )
    producer.flush()
    logger.info(s"Produced player with ID: ($id)")

  }
}
