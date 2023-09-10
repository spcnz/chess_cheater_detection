package yauza.chess.mapper

import yauza.avro.message.chess.{Move, MoveWithScore, ScoreType}
import yauza.chess.engine.Engine

trait ChessAnalysisMapper {
  def mapMove(chessEngine: Engine): Move => MoveWithScore = (move: Move) => {
    val (score, scoreType): (Long, ScoreType) = chessEngine.getPlayerScore(move.fen)

    MoveWithScore(
      gameId = move.gameId,
      fen = move.fen,
      lm = move.lm,
      playerScore = score,
      playerColor = chessEngine.getPointOfView(move.fen),
      moveIndex = move.moveIndex,
      scoreType = scoreType
    )
  }
}
