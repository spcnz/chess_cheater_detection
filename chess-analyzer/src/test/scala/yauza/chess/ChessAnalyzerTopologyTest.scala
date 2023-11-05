package yauza.chess

import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.{TestInputTopic, TestOutputTopic, Topology}
import org.scalatest.GivenWhenThen
import yauza.avro.message.chess._
import yauza.chess.engine.MockStockfishEngine

import java.util.Properties
import scala.reflect.io.Path

class ChessAnalyzerTopologyTest extends TopologyTest with Serdes with GivenWhenThen {

  var movesInputTopic: TestInputTopic[String, Move] = _
  var gamesInputTopic: TestInputTopic[String, Game] = _
  var playersInputTopic: TestInputTopic[String, Player] = _
  var gameResultInputTopic: TestInputTopic[String, GameResult] = _

  var moveScoreOutputTopic: TestOutputTopic[String, PlayerMove] = _
  var gameKpiOutputTopic: TestOutputTopic[String, GameKpi] = _
  var playerKpiOutputTopic: TestOutputTopic[String, PlayerKpi] = _

  override def schemaRegistryScope: String = classOf[ChessAnalyzerTopologyTest].toString

  override def stateDir: Option[Path] = Some(config.stateDir / config.applicationId)

  override def topology: Topology = TopologyBuilder(new MockStockfishEngine()).build

  behavior of "build"
  it should "not produce any message when there is no game messages" in {
    When("Move is played")
    publishMoveEvent(
      gameId = "1",
      fen = "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    When("No game message is produced")

    Then("No game kpi is produced")
    a[NoSuchElementException] shouldBe thrownBy(gameKpiOutputTopic.readValue)
  }
  it should "not produce any message when there is no match move-game" in {
    When("Move is played")
    publishMoveEvent(
      gameId = "1",
      fen = "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    When("Game message is produced")
    publishGameEvent(
      id = "2",
      rated = false
    )

    Then("No game kpi is produced")
    a[NoSuchElementException] shouldBe thrownBy(gameKpiOutputTopic.readValue)
  }
  it should "produce player move score with Stockfish evaluation" in {
    When("Game message is produced")
    publishGameEvent(
      id = "1",
      rated = false,
      black = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )
    When("Move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    When("No previous state of the game is available")

    Then("Move with Stockfish evaluation is produced")
    moveScoreOutputTopic.getQueueSize should be(1)
    val moveScore = moveScoreOutputTopic.readRecord()
    moveScore.key() should be("John_id")
    moveScore.value().id should be("John_id")
    moveScore.value().gameId should be("1")
    moveScore.value().lastMove should be("e2e4")
    moveScore.value().score should be(
      "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34".length
    )
    moveScore.value().label should be(MoveLabel.Good)
  }
  it should "filter out duplicate move messages" in {
    When("Game message is produced")
    publishGameEvent(
      id = "1",
      rated = false,
      black = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )
    When("No previous state of the game is available")
    When("Move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    When("Same move message is produced again")
    publishMoveEvent(
      gameId = "1",
      fen = "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )

    Then("Only one move with Stockfish evaluation is produced")
    moveScoreOutputTopic.getQueueSize should be(1)
    val moveScore = moveScoreOutputTopic.readRecord()
    moveScore.key() should be("John_id")
    moveScore.value().id should be("John_id")
    moveScore.value().gameId should be("1")
    moveScore.value().lastMove should be("e2e4")
    moveScore.value().score should be(
      "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34".length
    )
    moveScore.value().label should be(MoveLabel.Good)
  }
  it should "filter out duplicate game messages" in {
    When("Game message is produced")
    publishGameEvent(
      id = "1",
      rated = false,
      black = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )
    When("No previous state of the game is available")
    When("Move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    When("Same game message is produced again")
    publishGameEvent(
      id = "1",
      rated = false,
      black = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )

    Then("Only one move with Stockfish evaluation is produced")
    moveScoreOutputTopic.getQueueSize should be(1)
    val moveScore = moveScoreOutputTopic.readRecord()
    moveScore.key() should be("John_id")
    moveScore.value().id should be("John_id")
    moveScore.value().gameId should be("1")
    moveScore.value().lastMove should be("e2e4")
    moveScore.value().score should be(
      "2Q2nk1/4bp2/QB1p2q1/P3p3/5p1p/5b1P/5PR1/2R2K2 b - - 0 34".length
    )
    moveScore.value().label should be(MoveLabel.Good)
  }
  it should "update player's score with Stockfish evaluation" in {
    When("Game message is produced")
    publishGameEvent(
      id = "1",
      rated = false,
      white = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )
    When("Move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "Mocked engine return length of this property as score",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")
    When("Another move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "The score is negative length",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )

    Then("Move with Stockfish evaluation is produced")
    moveScoreOutputTopic.getQueueSize should be(2)
    moveScoreOutputTopic.readRecord()
    val playerMove = moveScoreOutputTopic.readRecord()
    playerMove.key() should be("John_id")
    playerMove.value().id should be("John_id")
    playerMove.value().gameId should be("1")
    playerMove.value().lastMove should be("e2e4")
    playerMove.value().score should be(-28)
    playerMove.value().label should be(MoveLabel.Inaccuracy)
  }
  it should "calculate player KPI per game" in {
    When("Game message is produced")
    publishGameEvent(
      id = "Game_ID",
      rated = false,
      white = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10))),
      status = Some("started")
    )
    When("Good move is played for corresponding game")
    publishMoveEvent(
      gameId = "Game_ID",
      fen = "Mocked engine return length of this property as score",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")
    When("Inaccuracy move is played for corresponding game")
    publishMoveEvent(
      gameId = "Game_ID",
      fen = "The score is negative length",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")
    When("Good move is played for corresponding game again")
    publishMoveEvent(
      gameId = "Game_ID",
      fen = "The score delta should be at least 50.",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Player KPI for corresponding game evaluation is produced")
    gameKpiOutputTopic.getQueueSize should be(3)
    gameKpiOutputTopic.readRecord()
    gameKpiOutputTopic.readRecord()
    val playerGameKpi = gameKpiOutputTopic.readRecord()
    playerGameKpi.key() should be("Game_ID")
    playerGameKpi.value().id should be("John_id")
    playerGameKpi.value().username should be("John")
    playerGameKpi.value().brilliantMoveCounter should contain(0L)
    playerGameKpi.value().excellentMoveCounter should contain(0L)
    playerGameKpi.value().goodMoveCounter should contain(2L)
    playerGameKpi.value().mistakeMoveCounter should contain(0L)
    playerGameKpi.value().inaccuracyMoveCounter should contain(1L)
    playerGameKpi.value().blunderMoveCounter should contain(0L)
    playerGameKpi.value().accuracy should contain(2 / 3.0)
  }
  it should "calculate player KPI globally when one game was played" in {
    When("Player message is produced")
    publishPlayerEvent(
      id = "John_id",
      username = "John",
      perfs = Some(
        perfs(bullet =
          Some(GameStatistic(games = 10, rating = 10, rd = 10, prog = 10, prov = Some(true)))
        )
      )
    )

    When("Game message is produced")
    publishGameEvent(
      id = "1",
      rated = false,
      white = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )
    When("Good move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "Mocked engine return length of this property as score",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")

    When("Inaccuracy move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "The score is negative length",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")

    When("Good move is played for corresponding game again")
    publishMoveEvent(
      gameId = "1",
      fen = "The score delta should be at least 50.",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Player KPI for corresponding game evaluation is produced")
    gameKpiOutputTopic.getQueueSize should be(3)
    gameKpiOutputTopic.readRecord()
    gameKpiOutputTopic.readRecord()
    val playerGameKpi = gameKpiOutputTopic.readRecord()
    playerGameKpi.key() should be("1")
    playerGameKpi.value().id should be("John_id")
    playerGameKpi.value().username should be("John")
    playerGameKpi.value().brilliantMoveCounter should contain(0L)
    playerGameKpi.value().excellentMoveCounter should contain(0L)
    playerGameKpi.value().goodMoveCounter should contain(2L)
    playerGameKpi.value().mistakeMoveCounter should contain(0L)
    playerGameKpi.value().inaccuracyMoveCounter should contain(1L)
    playerGameKpi.value().blunderMoveCounter should contain(0L)
    playerGameKpi.value().accuracy should contain(2 / 3.0)

    Then("Player KPI is produced")
    playerKpiOutputTopic.getQueueSize should be(3)
    playerKpiOutputTopic.readRecord()
    playerKpiOutputTopic.readRecord()
    val playerKpi = playerKpiOutputTopic.readRecord()
    playerKpi.key() should be("John_id")
    playerKpi.value().id should be("John_id")
    playerKpi.value().username should be("John")
    playerKpi.value().correctMoveCounter should contain(2L)
    playerKpi.value().incorrectMoveCounter should contain(1L)
    playerKpi.value().correctIncorrectMoveRatio should contain(2.0)
    playerKpi.value().meanPlayerAccuracy should contain(2 / 3.0)
    playerKpi.value().macroAccuracy should contain(2 / 3.0)
    playerKpi.value().accuracies should be(Map("1" -> 2 / 3.0))
    playerKpi.value().accuracySTD should contain(0.0)
  }
  it should "calculate player KPI globally when multiple games were played" in {
    When("Player message is produced")
    publishPlayerEvent(
      id = "John_id",
      username = "John",
      perfs = Some(
        perfs(bullet =
          Some(GameStatistic(games = 10, rating = 10, rd = 10, prog = 10, prov = Some(true)))
        )
      )
    )

    When("Game message is produced")
    publishGameEvent(
      id = "1",
      rated = false,
      white = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10)))
    )
    When("Good move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "Mocked engine return length of this property as score",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")

    When("Inaccuracy move is played for corresponding game")
    publishMoveEvent(
      gameId = "1",
      fen = "The score is negative length",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )
    Then("Move with Stockfish evaluation is produced")

    When("Good move is played for corresponding game again")
    publishMoveEvent(
      gameId = "1",
      fen = "The score delta should be at least 50.",
      lm = "e2e4",
      wc = 0,
      bc = 0,
      moveIndex = 1
    )

    When("Game result message is produced.")
    publishGameResultEvent(
      id = "1",
      winner = Some("white"),
      status = Some("resign"),
      rated = Some(false),
      players = GamePlayers(
        white = GamePlayer(GameUser("John", "John_id"), 10, Some(10)),
        black = GamePlayer(GameUser("Smith", "Smith_id"), 10, Some(10))
      )
    )
    Then("Player KPI for corresponding game evaluation is produced")
    playerKpiOutputTopic.getQueueSize should be(4)
    playerKpiOutputTopic.readRecord()
    playerKpiOutputTopic.readRecord()
    playerKpiOutputTopic.readRecord()
    val playerKpi = playerKpiOutputTopic.readRecord()
    playerKpi.key() should be("John_id")
    playerKpi.value().id should be("John_id")
    playerKpi.value().username should be("John")
    playerKpi.value().correctMoveCounter should contain(2L)
    playerKpi.value().incorrectMoveCounter should contain(1L)
    playerKpi.value().correctIncorrectMoveRatio should contain(2.0)
    playerKpi.value().meanPlayerAccuracy should contain(2 / 3.0)
    playerKpi.value().macroAccuracy should contain(2 / 3.0)
    playerKpi.value().accuracies should be(Map("1" -> 2 / 3.0))
    playerKpi.value().accuracySTD should contain(0.0)
    playerKpi.value().winLossRatio should contain(1.0)
    playerKpi.value().gameCount should contain(
      count(
        all = Some(1L),
        rated = None,
        draw = None,
        loss = None,
        win = Some(1L)
      )
    )
  }

  def publishMoveEvent(
      gameId: String,
      fen: String,
      lm: String,
      wc: Long,
      bc: Long,
      moveIndex: Long
  ): Unit =
    movesInputTopic.pipeInput(gameId, Move(Some(gameId), fen, lm, wc, bc, Some(moveIndex)))

  def publishPlayerEvent(
      id: String,
      username: String,
      perfs: Option[perfs] = None,
      title: Option[String] = None,
      createdAt: Option[Long] = None,
      profile: Option[profile] = None,
      seenAt: Option[Long] = None,
      playTime: Option[playTime] = None,
      url: Option[String] = None,
      count: Option[count] = None
  ): Unit =
    playersInputTopic.pipeInput(
      id,
      Player(
        id,
        username,
        perfs.getOrElse(new perfs),
        title,
        createdAt.getOrElse(0L),
        profile,
        seenAt.getOrElse(0L),
        playTime.getOrElse(new playTime),
        url.getOrElse(""),
        count
      )
    )
  def publishGameEvent(
      id: String,
      rated: Boolean,
      variant: Option[String] = Some("standard"),
      speed: Option[String] = Some("blitz"),
      perf: Option[String] = Some("blitz"),
      createdAt: Option[Long] = Some(0L),
      lastMoveAt: Option[Long] = Some(0L),
      status: Option[String] = Some("started"),
      white: Option[GamePlayer] = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10))),
      black: Option[GamePlayer] = Some(GamePlayer(GameUser("John", "John_id"), 10, Some(10))),
      moves: Option[String] = Some(""),
      clock: Option[GameClock] = Some(GameClock(10, 1, 0))
  ): Unit =
    gamesInputTopic.pipeInput(
      id,
      Game(
        id,
        rated,
        variant.get,
        speed.get,
        perf.get,
        createdAt.get,
        lastMoveAt.get,
        status.get,
        GamePlayers(white.get, black.get),
        moves.get,
        clock.get
      )
    )

  def publishGameResultEvent(
      id: String,
      winner: Option[String] = Some("white"),
      status: Option[String] = Some("resign"),
      rated: Option[Boolean] = Some(false),
      players: GamePlayers
  ): Unit =
    gameResultInputTopic.pipeInput(
      id,
      GameResult(
        id = id,
        winner = winner.get,
        status = GameResultStatus(1, status.get),
        rated = rated.get,
        fen = "a4b5c6d7e8f9g0h1",
        lastMove = "e2e4",
        players = players,
        createdAt = 0L,
        variant = GameResultVariant("1", "standard", "std"),
        speed = "blitz",
        perf = "blitz",
        check = Some("false"),
        rematch = Some("false"),
        turns = 10,
        player = "white",
        source = "lila",
        startedAtTurn = Some(1L)
      )
    )

  override def createInputTopics(): Unit = {
    movesInputTopic = driver.createInputTopic(
      config.chessAnalyzer.topic.input.move,
      stringSerde.serializer(),
      moveSerde.serializer()
    )

    gamesInputTopic = driver.createInputTopic(
      config.chessAnalyzer.topic.input.game,
      stringSerde.serializer(),
      gameSerde.serializer()
    )

    playersInputTopic = driver.createInputTopic(
      config.chessAnalyzer.topic.input.player,
      stringSerde.serializer(),
      playerSerde.serializer()
    )

    gameResultInputTopic = driver.createInputTopic(
      config.chessAnalyzer.topic.input.gameResult,
      stringSerde.serializer(),
      gameResult.serializer()
    )

  }

  override def createOutputTopics(): Unit = {
    moveScoreOutputTopic = driver.createOutputTopic(
      Seq(
        properties.get("application.id"),
        config.chessAnalyzer.topic.sink.playerMoveScore,
        "repartition"
      ).mkString("-"),
      stringSerde.deserializer(),
      playerMoveSerde.deserializer()
    )
    gameKpiOutputTopic = driver.createOutputTopic(
      Seq(
        properties.get("application.id"),
        config.chessAnalyzer.topic.sink.gameKpi,
        "repartition"
      ).mkString("-"),
      stringSerde.deserializer(),
      gameKpiSerde.deserializer()
    )
    playerKpiOutputTopic = driver.createOutputTopic(
      config.chessAnalyzer.topic.sink.playerKpi,
      stringSerde.deserializer(),
      playerKpiSerde.deserializer()
    )
  }

  override def properties: Properties = config.streamProperties

}
