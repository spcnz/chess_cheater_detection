package yauza.chess.mapper

import yauza.avro.message.chess._
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

  behavior of "mapPlayerKpi"
  it should "map player to player with kpi initialized" in {
    val player = Player(
      id = "playerID",
      username = "player1",
      perfs = perfs(bullet =
        Some(
          GameStatistic(
            games = 10,
            rating = 1200,
            rd = 100,
            prog = 12,
            prov = Some(true)
          )
        )
      ),
      title = Some("GM"),
      createdAt = 100L,
      profile = None,
      seenAt = 101L,
      playTime = playTime(
        total = 1000,
        tv = 1
      ),
      url = "http://player1.com",
      count = Some(
        count(
          all = Some(100),
          rated = Some(50),
          draw = Some(60),
          loss = Some(100),
          win = Some(60)
        )
      )
    )
    val playerKpi = mapPlayerKpi(player)
    playerKpi.id should be("playerID")
    playerKpi.username should be("player1")
    playerKpi.bulletGameStatistic should contain(
      GameStatistic(
        games = 10,
        rating = 1200,
        rd = 100,
        prog = 12,
        prov = Some(true)
      )
    )
    playerKpi.title should be(Some("GM"))
    playerKpi.createdAt should be(100L)
    playerKpi.country should be(None)
    playerKpi.seenAt should be(101L)
    playerKpi.playTime should contain(1000)
    playerKpi.url should be("http://player1.com")
    playerKpi.gameCount should contain(
      count(
        all = Some(100),
        rated = Some(50),
        draw = Some(60),
        loss = Some(100),
        win = Some(60)
      )
    )
    playerKpi.incorrectMoveCounter should be(None)
    playerKpi.correctMoveCounter should be(None)
    playerKpi.correctIncorrectMoveRatio should be(None)
    playerKpi.winLossRatio should contain(0.6)
    playerKpi.meanPlayerAccuracy should be(None)
    playerKpi.macroAccuracy should be(None)
    playerKpi.accuracies should be(Map())
    playerKpi.accuracySTD should be(None)
    playerKpi.accuracyMedian should be(None)

  }
}
