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
  var moveScoreOutputTopic: TestOutputTopic[String, PlayerMove] = _
  var playerGameKpiOutputTopic: TestOutputTopic[String, PlayerGameKpi] = _

  override def schemaRegistryScope: String = classOf[ChessAnalyzerTopologyTest].toString

  override def stateDir: Option[Path] = Some(config.stateDir / config.applicationId)

  override def topology: Topology = TopologyBuilder(new MockStockfishEngine()).build

  def publishMoveEvent(
      gameId: String,
      fen: String,
      lm: String,
      wc: Long,
      bc: Long,
      moveIndex: Long
  ): Unit =
    movesInputTopic.pipeInput(gameId, Move(Some(gameId), fen, lm, wc, bc, Some(moveIndex)))

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
    a[NoSuchElementException] shouldBe thrownBy(playerGameKpiOutputTopic.readValue)
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
    a[NoSuchElementException] shouldBe thrownBy(playerGameKpiOutputTopic.readValue)
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
    playerGameKpiOutputTopic.getQueueSize should be(3)
    playerGameKpiOutputTopic.readRecord()
    playerGameKpiOutputTopic.readRecord()
    val playerGameKpi = playerGameKpiOutputTopic.readRecord()
    playerGameKpi.key() should be("John_id")
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
    playerGameKpiOutputTopic = driver.createOutputTopic(
      Seq(
        properties.get("application.id"),
        config.chessAnalyzer.topic.sink.playerGameKpi,
        "repartition"
      ).mkString("-"),
      stringSerde.deserializer(),
      playerGameKpiSerde.deserializer()
    )
  }

  override def properties: Properties = config.streamProperties

}
