package yauza.chess.processor

import org.apache.kafka.streams.processor.api.{MockProcessorContext, Record}
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.state.{KeyValueStore, Stores}
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import yauza.avro.message.chess._
import yauza.chess.`enum`.{PlayerColor, ScoreType}
import yauza.chess.{Serdes, UnitTest}

class GameScoreProcessorTest
    extends UnitTest
    with BeforeAndAfterEach
    with GivenWhenThen
    with Serdes {

  val GameScoreStoreName: String = "test-game-score-store"
  var processorContext: MockProcessorContext[String, PlayerMove] = _
  var processor: GameScoreProcessor = _
  var gameScoreStore: KeyValueStore[String, GameScore] = _

  override def beforeEach(): Unit = {
    processorContext = new MockProcessorContext()

    gameScoreStore = Stores
      .keyValueStoreBuilder(
        Stores.inMemoryKeyValueStore(GameScoreStoreName),
        stringSerde,
        gameScoreSerde
      )
      .withLoggingDisabled // Logging must be disabled with MockProcessorContext.
      .build

    gameScoreStore.init(processorContext.getStateStoreContext, gameScoreStore)
    processorContext.getStateStoreContext.register(gameScoreStore, (_, _) => {})
    processor = GameScoreProcessor(GameScoreStoreName)
    processor.init(processorContext)
  }

  override def afterEach(): Unit = {
    processor.close()
    gameScoreStore.close()
  }

  behavior of "process"
  it should "initialize game score and forward it to downstream" in {
    Given("No previous score exists")
    val gameWithMoveScore = GameWithMoveScore(
      id = "test-game-id",
      playerColor = PlayerColor.White,
      playerScore = 100,
      scoreType = ScoreType.Mate,
      moveIndex = Some(10L),
      fen = "CHANGED",
      lastMove = "CHANGED",
      rated = true,
      variant = "standard",
      speed = "blitz",
      perf = "blitz",
      createdAt = 0,
      lastMoveAt = 0,
      status = "started",
      moves = "",
      clock = GameClock(0, 0, 0),
      players = GamePlayers(
        GamePlayer(
          GameUser("white-user-name", "white-user-id"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("black-user-name", "black-user-id"),
          10,
          Some(10)
        )
      )
    )
    When("Game score processor is called")
    processor.process(
      record = new Record[String, GameWithMoveScore](
        "white-user-id|test-game-id",
        gameWithMoveScore,
        1L
      )
    )
    Then("Game score store is updated")
    val gameScore = gameScoreStore.get("test-game-id")
    gameScore should not be None
    gameScore.blackScore should be(0)
    gameScore.whiteScore should be(100)

    And("Player move is forwarded to downstream")
    val forwarded = processorContext.forwarded
    forwarded.size() should be(1)
    forwarded.get(0).record().key() should be("white-user-id")
    forwarded.get(0).record().value().gameId should be("test-game-id")
    forwarded.get(0).record().value().username should be("white-user-name")
    forwarded.get(0).record().value().score should be(100)
    forwarded.get(0).record().value().lastMove should be("CHANGED")
    forwarded.get(0).record().value().label should be(MoveLabel.Excellent)
    forwarded.get(0).record().value().gameStatus should be("started")
  }
  it should "update game score and forward it to downstream" in {
    Given("There is previous score in state store")
    gameScoreStore.put(
      "test-game-id",
      GameScore(
        id = "test-game-id",
        rated = true,
        variant = "standard",
        speed = "blitz",
        perf = "blitz",
        createdAt = 10L,
        lastMoveAt = 11L,
        status = "started",
        players = GamePlayers(
          GamePlayer(
            GameUser("white-user-name", "white-user-id"),
            10,
            Some(10)
          ),
          GamePlayer(
            GameUser("black-user-name", "black-user-id"),
            10,
            Some(10)
          )
        ),
        moves = "",
        clock = GameClock(0, 0, 0),
        whiteScore = 100,
        blackScore = -53,
        fen = "",
        lastMove = "a2a4"
      )
    )

    When("Brilliant move is played")
    val gameWithMoveScore = GameWithMoveScore(
      id = "test-game-id",
      playerColor = PlayerColor.White,
      playerScore = 300,
      scoreType = ScoreType.Centipawn,
      moveIndex = Some(10L),
      fen = "CHANGED",
      lastMove = "CHANGED",
      rated = true,
      variant = "standard",
      speed = "blitz",
      perf = "blitz",
      createdAt = 0,
      lastMoveAt = 0,
      status = "started",
      moves = "",
      clock = GameClock(0, 0, 0),
      players = GamePlayers(
        GamePlayer(
          GameUser("white-user-name", "white-user-id"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("black-user-name", "black-user-id"),
          10,
          Some(10)
        )
      )
    )

    When("Game score processor is called")
    processor.process(
      record = new Record[String, GameWithMoveScore](
        "white-user-id|test-game-id",
        gameWithMoveScore,
        1L
      )
    )
    Then("Game score store is updated")
    val gameScore = gameScoreStore.get("test-game-id")
    gameScore should not be None
    gameScore.blackScore should be(-53)
    gameScore.whiteScore should be(300)

    And("Player move is forwarded to downstream")
    val forwarded = processorContext.forwarded
    forwarded.size() should be(1)
    forwarded.get(0).record().key() should be("white-user-id")
    forwarded.get(0).record().value().gameId should be("test-game-id")
    forwarded.get(0).record().value().username should be("white-user-name")
    forwarded.get(0).record().value().score should be(300)
    forwarded.get(0).record().value().lastMove should be("CHANGED")
    forwarded.get(0).record().value().label should be(MoveLabel.Brilliant)
  }

  behavior of "updateGameScore"
  it should "update game score for player and save it to state store" in {
    val gameScore = GameScore(
      id = "test-game-id",
      rated = true,
      variant = "standard",
      speed = "blitz",
      perf = "blitz",
      createdAt = 10L,
      lastMoveAt = 11L,
      status = "started",
      players = GamePlayers(
        GamePlayer(
          GameUser("white-user-name", "white-user-id"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("black-user-name", "black-user-id"),
          10,
          Some(10)
        )
      ),
      moves = "",
      clock = GameClock(0, 0, 0),
      whiteScore = 100,
      blackScore = -53,
      fen = "",
      lastMove = "a2a4"
    )

    val gameWithMoveScore = GameWithMoveScore(
      id = "test-game-id",
      playerColor = PlayerColor.White,
      playerScore = 100,
      scoreType = ScoreType.Mate,
      moveIndex = Some(10L),
      fen = "CHANGED",
      lastMove = "CHANGED",
      rated = true,
      variant = "standard",
      speed = "blitz",
      perf = "blitz",
      createdAt = 0,
      lastMoveAt = 0,
      status = "started",
      moves = "",
      clock = GameClock(0, 0, 0),
      players = GamePlayers(
        GamePlayer(
          GameUser("test-user-id", "test-user-name"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("test-user-id", "test-user-name"),
          10,
          Some(10)
        )
      )
    )

    processor.updateGameScore(gameScore, gameWithMoveScore)
    gameScoreStore.get("test-game-id") should be(
      gameScore.copy(lastMove = "CHANGED", fen = "CHANGED", whiteScore = 100)
    )

  }

  behavior of "getLabel"
  it should "return brilliant move label based on score delta" in {
    processor.getLabel(previousScore = -20, newScore = 182) should be(MoveLabel.Brilliant)
  }
  it should "return good excellent label based on score delta" in {
    processor.getLabel(previousScore = -20, newScore = 82) should be(MoveLabel.Excellent)
  }
  it should "return good move label based on score delta" in {
    processor.getLabel(previousScore = -20, newScore = 35) should be(MoveLabel.Good)
  }
  it should "return neutral move label based on positive score delta" in {
    processor.getLabel(previousScore = -20, newScore = -10) should be(MoveLabel.Neutral)
  }
  it should "return neutral move label based on negative score delta" in {
    processor.getLabel(previousScore = -20, newScore = -30) should be(MoveLabel.Neutral)
  }
  it should "return inaccuracy move label based on score delta" in {
    processor.getLabel(previousScore = -20, newScore = -70) should be(MoveLabel.Inaccuracy)
  }
  it should "return mistake move label based on score delta" in {
    processor.getLabel(previousScore = 20, newScore = -80) should be(MoveLabel.Mistake)
  }
  it should "return blunder move label based on score delta" in {
    processor.getLabel(previousScore = 20, newScore = -180) should be(MoveLabel.Blunder)
  }

  behavior of "getPlayerInfo"
  it should "return white's player id, name and score" in {
    val gameScore = GameScore(
      id = "1",
      rated = true,
      variant = "standard",
      speed = "blitz",
      perf = "blitz",
      createdAt = 10L,
      lastMoveAt = 11L,
      status = "started",
      players = GamePlayers(
        GamePlayer(
          GameUser("white-user-name", "white-user-id"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("black-user-name", "black-user-id"),
          10,
          Some(10)
        )
      ),
      moves = "",
      clock = GameClock(0, 0, 0),
      whiteScore = 100,
      blackScore = -53,
      fen = "",
      lastMove = "a2a4"
    )
    processor.getPlayerInfo(gameScore, PlayerColor.White) should be(
      ("white-user-id", "white-user-name", 100)
    )
  }

  behavior of "initialGameScore"
  it should "initialize game score" in {
    val gameWithMoveScore = GameWithMoveScore(
      id = "test-game-id",
      playerColor = PlayerColor.White,
      fen = "a2a3",
      lastMove = "a2a3",
      rated = true,
      variant = "standard",
      speed = "blitz",
      perf = "blitz",
      createdAt = 0,
      lastMoveAt = 0,
      status = "started",
      moves = "",
      clock = GameClock(0, 0, 0),
      players = GamePlayers(
        GamePlayer(
          GameUser("test-user-id", "test-user-name"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("test-user-id", "test-user-name"),
          10,
          Some(10)
        )
      ),
      playerScore = 100,
      scoreType = ScoreType.Mate,
      moveIndex = Some(10L)
    )

    val gameScore = processor.initialGameScore(gameWithMoveScore)

    gameScore.id should be(gameWithMoveScore.id)
    gameScore.whiteScore should be(0)
    gameScore.blackScore should be(0)
    gameScore.fen should be("a2a3")
    gameScore.lastMove should be("a2a3")
    gameScore.rated should be(true)
    gameScore.variant should be("standard")
    gameScore.speed should be("blitz")
    gameScore.perf should be("blitz")
    gameScore.createdAt should be(0)
    gameScore.lastMoveAt should be(0)
    gameScore.status should be("started")
    gameScore.moves should be("")
    gameScore.clock should be(GameClock(0, 0, 0))
    gameScore.players should be(
      GamePlayers(
        GamePlayer(
          GameUser("test-user-id", "test-user-name"),
          10,
          Some(10)
        ),
        GamePlayer(
          GameUser("test-user-id", "test-user-name"),
          10,
          Some(10)
        )
      )
    )

  }
}
