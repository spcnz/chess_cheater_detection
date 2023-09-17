package yauza.chess.joiner

import yauza.avro.message.chess.{Game, GameWithMoveScore, MoveWithScore}

trait MoveGameJoiner {

  def joinMoveWithGame: (MoveWithScore, Game) => GameWithMoveScore =
    (move: MoveWithScore, game: Game) =>
      GameWithMoveScore(
        id = game.id,
        rated = game.rated,
        variant = game.variant,
        speed = game.speed,
        perf = game.perf,
        createdAt = game.createdAt,
        lastMoveAt = game.lastMoveAt,
        status = game.status,
        players = game.players,
        moves = game.moves,
        clock = game.clock,
        fen = move.fen,
        lastMove = move.lm,
        moveIndex = move.moveIndex,
        playerScore = move.playerScore,
        playerColor = move.playerColor,
        scoreType = move.scoreType
      )
}
