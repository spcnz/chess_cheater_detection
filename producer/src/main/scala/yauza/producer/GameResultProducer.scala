package yauza.producer

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import yauza.api.Api
import yauza.avro.message.chess.GameResult
import yauza.config.Configuration
import yauza.decoder.Decoder

import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global

case class GameResultProducer(api: Api)(implicit config: Configuration)
    extends Producer
    with Decoder
    with LazyLogging {
  val producer = new KafkaProducer[String, GameResult](super.props)
  implicit val actorSystem: ActorSystem = ActorSystem("sttp")

  override def produce(): Unit =
    while (true) {
      api.getGames.foreach(game => streamGameResults(game.id))
      Thread.sleep(config.app.sleepTimeMs)
    }

  def streamGameResults(gameId: String): Unit =
    api
      .getGameResult(gameId)
      .andThen { case Success(response) =>
        response.body match {
          case Right(streamSource) =>
            streamSource
              .map(_.utf8String)
              .map(decodeGameResult)
              .runForeach(_.foreach(sendGameResult(_, gameId)))
          case e => logger.info(s"Exception occurred while getting game results: $e")
        }
      }

  def sendGameResult(gameResult: GameResult, gameId: String): Unit = {
    producer.send(
      new ProducerRecord(
        config.kafka.topics.gameResults,
        gameId,
        gameResult
      )
    )
    producer.flush()
    logger.info(s"Produced result for: ($gameId)")
  }

}
