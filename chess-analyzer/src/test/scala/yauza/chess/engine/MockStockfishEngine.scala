package yauza.chess.engine

import yauza.chess.`enum`.{PlayerColor, ScoreType}

class MockStockfishEngine extends Engine {
  override def start(): Unit = {}

  override def getPointOfView(fen: String): String = PlayerColor.White

  override def getPlayerScore(fen: String): (Long, String) =
    if (fen.length > 30)
      (fen.length, ScoreType.Centipawn)
    else
      (-1 * fen.length, ScoreType.Centipawn)

  override private[engine] def setPosition(fen: String): Unit = {}
}
