package yauza.chess.joiner

import yauza.avro.message.chess._
import yauza.chess.UnitTest

class GameKpiGameResultJoinerTest extends UnitTest with GameKpiGameResultJoiner {

  behavior of "joinGameKpiWithGameResult"
  it should "join game game kpi with the result and update status and winner fields" in {
    val gameKpi = GameKpi(
      id = "test-player-id",
      username = "test-player-username",
      gameId = "test-game-id-2",
      gameStatus = "started",
      brilliantMoveCounter = Some(1),
      excellentMoveCounter = Some(2),
      goodMoveCounter = Some(3),
      inaccuracyMoveCounter = Some(4),
      mistakeMoveCounter = Some(5),
      blunderMoveCounter = Some(1),
      accuracy = Some(0.375)
    )

    val gameResult = GameResult(
      id = "test-game-id-2",
      status = GameResultStatus(1, "mate"),
      winner = "white",
      rated = true,
      speed = "bullet",
      perf = "bullet",
      createdAt = 1234567890L,
      variant = GameResultVariant("1", "standard", "std"),
      turns = 10,
      startedAtTurn = Some(1L),
      fen = "test-fen",
      lastMove = "test-last-move",
      source = "source",
      player = "white",
      check = None,
      rematch = Some("true"),
      players = GamePlayers(
        GamePlayer(GameUser("whiteName", "whiteId"), 1000, ratingDiff = Some(10)),
        GamePlayer(GameUser("blackName", "blackId"), 1900, ratingDiff = Some(10))
      )
    )
    val actual = joinGameKpiWithGameResult(gameKpi, gameResult)
    val expected = gameKpi.copy(
      gameStatus = "mate",
      winner = Some("whiteId"),
      rated = Some(true)
    )

  }
}
