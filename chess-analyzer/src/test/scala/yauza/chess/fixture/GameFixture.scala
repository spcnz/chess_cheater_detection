package yauza.chess.fixture

import yauza.avro.message.chess.{Game, GameClock, GamePlayer, GamePlayers, GameUser}

trait GameFixture {

  def gameEvent(
      id: String = "100",
      rated: Boolean = true,
      variant: String = "standard",
      speed: String = "bullet",
      perf: String = "bullet",
      createdAt: Long = 1234567890L,
      lastMoveAt: Long = 1234567891L,
      status: String = "started",
      whiteName: String = "white_name",
      whiteId: String = "white_ID",
      whiteRating: Int = 1000,
      blackName: String = "black_name",
      blackId: String = "black_ID",
      blackRating: Int = 1000,
      moves: String = "1. e4 e5 2. Nf3 Nc6",
      clock: GameClock = GameClock(initial = 0, increment = 0, totalTime = 0)
  ): Game =
    Game(
      id = id,
      rated = rated,
      variant = variant,
      speed = speed,
      perf = perf,
      createdAt = createdAt,
      lastMoveAt = lastMoveAt,
      status = status,
      players = GamePlayers(
        GamePlayer(GameUser(whiteName, whiteId), whiteRating),
        GamePlayer(GameUser(blackName, blackId), blackRating)
      ),
      moves = moves,
      clock = clock
    )

}
