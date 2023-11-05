package yauza.chess.joiner

import yauza.avro.message.chess.{GameKpi, GameResult}

trait GameKpiGameResultJoiner {

  def joinGameKpiWithGameResult: (GameKpi, GameResult) => GameKpi =
    (gameKpi: GameKpi, gameResult: GameResult) =>
      gameKpi.copy(
        gameStatus = Option(gameResult).map(_.status.name).getOrElse(gameKpi.gameStatus),
        winner = Option(gameResult).map(_.winner) match {
          case Some("white") => Some(gameResult.players.white.user.id)
          case Some("black") => Some(gameResult.players.black.user.id)
          case _             => None
        },
        rated = Option(gameResult).map(_.rated)
      )
}
