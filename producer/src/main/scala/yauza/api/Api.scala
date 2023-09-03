package yauza.api

import sttp.capabilities
import sttp.capabilities.akka.AkkaStreams
import sttp.capabilities.akka.AkkaStreams.BinaryStream
import sttp.client3.{HttpURLConnectionBackend, Identity, Response, SttpBackend}
import sttp.client3.akkahttp.AkkaHttpBackend
import yauza.avro.message.chess.{Game, Player}

import scala.concurrent.Future

trait Api {
  val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
  val akkaBackend: SttpBackend[Future, AkkaStreams with capabilities.WebSockets] =
    AkkaHttpBackend()

  def getGames: Array[Game]
  def getMoves(gameId: String): Future[Response[Either[String, BinaryStream]]]
  def getPlayer(id: String): Player
  def getGameResult(id: String): Future[Response[Either[String, BinaryStream]]]
}
