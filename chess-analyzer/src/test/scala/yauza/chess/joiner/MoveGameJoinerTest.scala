package yauza.chess.joiner

import yauza.avro.message.chess._
import yauza.chess.UnitTest

class MoveGameJoinerTest extends UnitTest with MoveGameJoiner {

  behavior of "joinMoveWithGame"
  it should "join move with game" in {
    val move = MoveWithScore(
      fen = "4r1k1/pp3pbp/2PB2p1/1P6/8/3p3P/1PnN1PP1/1R3K2 b - - 0 29",
      lm = "c5c4",
      moveIndex = Some(10),
      playerScore = 100,
      playerColor = "white",
      scoreType = "CENTIPAWN"
    )
    val game = Game(
      id = "100",
      rated = true,
      variant = "standard",
      speed = "bullet",
      perf = "bullet",
      createdAt = 1234567890L,
      lastMoveAt = 1234567891L,
      status = "started",
      players = GamePlayers(
        GamePlayer(GameUser("whiteName", "whiteId"), 1000, ratingDiff = Some(10)),
        GamePlayer(GameUser("blackName", "blackId"), 1900, ratingDiff = Some(10))
      ),
      moves = "1. e4 e5 2. Nf3 Nc6",
      clock = GameClock(initial = 0, increment = 0, totalTime = 0)
    )

    val result = joinMoveWithGame(move, game)
    val expected = GameWithMoveScore(
      id = "100",
      rated = true,
      variant = "standard",
      speed = "bullet",
      perf = "bullet",
      createdAt = 1234567890L,
      lastMoveAt = 1234567891L,
      status = "started",
      players = GamePlayers(
        GamePlayer(GameUser("whiteName", "whiteId"), 1000, ratingDiff = Some(10)),
        GamePlayer(GameUser("blackName", "blackId"), 1900, ratingDiff = Some(10))
      ),
      moves = "1. e4 e5 2. Nf3 Nc6",
      clock = GameClock(initial = 0, increment = 0, totalTime = 0),
      fen = "4r1k1/pp3pbp/2PB2p1/1P6/8/3p3P/1PnN1PP1/1R3K2 b - - 0 29",
      lastMove = "c5c4",
      moveIndex = Some(10),
      playerScore = 100,
      playerColor = "white",
      scoreType = "CENTIPAWN"
    )

    result shouldEqual expected
  }

}
