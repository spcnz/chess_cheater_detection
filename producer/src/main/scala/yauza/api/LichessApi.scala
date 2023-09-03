package yauza.api

import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import sttp.capabilities.akka.AkkaStreams
import sttp.capabilities.akka.AkkaStreams.BinaryStream
import sttp.client3.{asStreamUnsafe, basicRequest, Response, UriContext}
import yauza.avro.message.chess.{Game, Player}
import yauza.config.Configuration
import yauza.decoder.Decoder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure

case class LichessApi()(implicit config: Configuration) extends Api with LazyLogging with Decoder {

  override def getGames: Array[Game] = {
    val response = basicRequest
      .header("Accept", "application/x-ndjson")
      .get(uri"${config.api.games}")
      .send(backend)

    response.body match {
      case Right(games) => decodeGames(games)
      case Left(e) =>
        throw new RuntimeException(s"Games not available. Exception: $e")
    }
  }

  override def getMoves(
      gameId: String
  ): Future[Response[Either[String, BinaryStream]]] =
    basicRequest
      .get(uri"${config.api.moves.replace("{id}", gameId)}")
      .response(asStreamUnsafe(AkkaStreams))
      .send(akkaBackend)
      .andThen { case Failure(e) =>
        logger.info(s"Moves not available. Exception: $e")
      }

  override def getGameResult(
      gameId: String
  ): Future[Response[Either[String, Source[ByteString, Any]]]] =
    basicRequest
      .get(uri"${config.api.gameResults.replace("{id}", gameId)}")
      .response(asStreamUnsafe(AkkaStreams))
      .send(akkaBackend)
      .andThen { case Failure(e) =>
        logger.info(s"Game results not available. Exception: $e")
      }

  override def getPlayer(id: String): Player = {
    val response = basicRequest
      .header("Accept", "application/x-ndjson")
      .get(uri"${config.api.player.replace("{id}", id)}")
      .send(backend)

    response.body match {
      case Right(player) => decodePlayer(player)
      case Left(e) =>
        throw new RuntimeException(s"Players not available. Exception: $e")
    }
  }
}
