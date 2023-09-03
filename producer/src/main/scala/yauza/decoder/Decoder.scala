package yauza.decoder

import io.circe.jawn.decode
import io.circe.generic.auto._
import yauza.avro.message.chess.{Game, GameResult, Move, Player}

trait Decoder {

  val delimiter = "\n"

  def decodeGames(encodedGames: String): Array[Game] =
    encodedGames
      .split(delimiter)
      .map { encodedGame =>
        decode[Game](encodedGame) match {
          case Right(game) => game
          case Left(err) =>
            throw new RuntimeException(s"Error while decoding game $err, $encodedGame")
        }
      }

  def decodeMoves(encodedMoves: String): Array[Move] =
    encodedMoves
      .split(delimiter)
      .map { encodedMove =>
        decode[Move](encodedMove) match {
          case Right(move) => move
          case Left(_)     => None
        }
      }
      .collect { case move: Move => move }

  def decodeGameResult(encodedGameResults: String): Array[GameResult] =
    encodedGameResults
      .split(delimiter)
      .map { encodedGameResult =>
        decode[GameResult](encodedGameResult) match {
          case Right(gameResult) => gameResult
          case Left(_)           => None
        }
      }
      .collect { case gameResult: GameResult => gameResult }

  def decodePlayer(encodedPlayer: String): Player =
    decode[Player](encodedPlayer) match {
      case Right(player) => player
      case Left(err) =>
        throw new RuntimeException(s"Error while decoding player $err, $encodedPlayer")
    }

}
