package yauza.chess.mapper

import yauza.avro.message.chess._

trait ChessAnalysisMapper {
  def mapMove(score: Long, scoreType: String, playerColor: String): Move => MoveWithScore =
    (move: Move) =>
      MoveWithScore(
        gameId = move.gameId,
        fen = move.fen,
        lm = move.lm,
        playerScore = score,
        playerColor = playerColor,
        moveIndex = move.moveIndex,
        scoreType = scoreType
      )
}
