package yauza.chess.engine

class MockStockfishEngine extends Engine {
  override def start(): Unit = {}

  override private[engine] def setPosition(fen: String): Unit = {}

  override def getPointOfView(fen: String) = "???"

  override def getPlayerScore(fen: String) = (100, "CENTIPAWN")
}
