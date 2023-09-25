package yauza.chess.mapper

import yauza.avro.message.chess.Move
import yauza.chess.UnitTest
import yauza.chess.`enum`.{PlayerColor, ScoreType}

class ChessAnalysisMapperTest extends UnitTest with ChessAnalysisMapper {

  behavior of "mapMove"
  it should "map move to move with score" in {
    val move = Move(
      gameId = Some("100"),
      fen = "4r1k1/pp3pbp/2PB2p1/1P6/8/3p3P/1PnN1PP1/1R3K2 b - - 0 29",
      lm = "c5c6",
      moveIndex = Some(10),
      wc = 10,
      bc = 20
    )
    val moveWithScore =
      mapMove(score = -100, scoreType = ScoreType.Mate, playerColor = PlayerColor.Black)(move)
    moveWithScore.gameId should contain("100")
    moveWithScore.fen should be("4r1k1/pp3pbp/2PB2p1/1P6/8/3p3P/1PnN1PP1/1R3K2 b - - 0 29")
    moveWithScore.lm should be("c5c6")
    moveWithScore.moveIndex should contain(10)
    moveWithScore.playerScore should be(-100)
    moveWithScore.playerColor should be(PlayerColor.Black)
    moveWithScore.scoreType should be(ScoreType.Mate)
  }
}
