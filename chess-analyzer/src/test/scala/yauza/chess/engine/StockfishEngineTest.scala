package yauza.chess.engine

import yauza.chess.UnitTest
import yauza.chess.`enum`.{PlayerColor, ScoreType}

class StockfishEngineTest extends UnitTest {

  val engine = StockfishEngine(depth = 1, searchTimeMillis = 1000)

  behavior of "getPointOfView"
  it should "return color white as point of view" in {
    engine.getPointOfView("8/1k6/4n3/1BK5/3B4/1PP2b2/8/8 w - - 7 48") should be(PlayerColor.White)
  }
  it should "return color black as point of view" in {
    engine.getPointOfView("8/1k6/4n3/1BK5/3B4/1PP2b2/8/8 b - - 7 48") should be(PlayerColor.Black)
  }

  behavior of "extractScore"
  it should "extract centipawn score from Stockfish's response" in {
    val scoreInfo =
      "info depth 1 seldepth 1 multipv 1 score cp 1000 nodes 1 nps 1000 tbhits 0 time 1 pv e2e4"
    engine.extractScore(
      scoreInfo,
      engine.centipawnScorePattern,
      engine.centipawnPrefix.length,
      ScoreType.Centipawn
    ) should contain(1000)
  }
  it should "extract mate score from Stockfish's response" in {
    val scoreInfo =
      "info depth 1 seldepth 1 multipv 1 score mate 5 nodes 1 nps 1000 tbhits 0 time 1 pv e2e4"
    engine.extractScore(
      scoreInfo,
      engine.mateScorePattern,
      engine.matePrefix.length,
      ScoreType.Mate
    ) should contain(5)
  }
  it should "return None when score is not defined in Stockfish's response" in {
    val scoreInfo =
      "info depth 1 seldepth 1 multipv 1 score  nodes 1 nps 1000 tbhits 0 time 1 pv e2e4"

    engine.extractScore(
      scoreInfo,
      engine.mateScorePattern,
      engine.matePrefix.length,
      ScoreType.Mate
    ) should be(None)
  }
}
