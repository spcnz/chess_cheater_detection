package yauza.producer

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import yauza.config.Configuration
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import yauza.api.Api
import yauza.avro.message.chess.Move
import yauza.decoder.Decoder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

case class MoveProducer(api: Api)(implicit config: Configuration)
    extends Producer
    with Decoder
    with LazyLogging {

  val producer = new KafkaProducer[String, Move](super.props)
  implicit val actorSystem: ActorSystem = ActorSystem("sttp")

  override def produce(): Unit =
    while (true) {
      api.getGames.foreach(game => streamMoves(game.id))
      Thread.sleep(config.app.sleepTimeMs)
    }

  def streamMoves(gameId: String): Unit = {
    var lastMoveIndex = 0
    api
      .getMoves(gameId)
      .andThen { case Success(response) =>
        response.body match {
          case Right(streamSource) =>
            streamSource
              .map(_.utf8String)
              .map(decodeMoves)
              .runForeach { moves =>
                moves.zipWithIndex.foreach(moveWithIndex =>
                  sendMove(moveWithIndex._1, gameId, moveWithIndex._2 + lastMoveIndex)
                )
                lastMoveIndex += moves.length
              }
          case e => logger.info(s"Exception occurred while getting moves: $e")
        }
      }
  }

  def sendMove(move: Move, gameId: String, moveIndex: Int): Unit = {
    move.gameId = Some(gameId)
    move.moveIndex = Some(moveIndex)
    producer.send(
      new ProducerRecord(
        config.kafka.topics.moves,
        gameId,
        move
      )
    )
    producer.flush()
    logger.info(s"Produced message: ($gameId, ${move.fen})")
  }
}
