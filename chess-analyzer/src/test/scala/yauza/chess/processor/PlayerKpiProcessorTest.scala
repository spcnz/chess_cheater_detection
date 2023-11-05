package yauza.chess.processor

import org.apache.kafka.streams.processor.api.{MockProcessorContext, Record}
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.state.{Stores, TimestampedKeyValueStore, ValueAndTimestamp}
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import yauza.avro.message.chess._
import yauza.chess.{Serdes, UnitTest}

import java.time.Instant

class PlayerKpiProcessorTest
    extends UnitTest
    with BeforeAndAfterEach
    with GivenWhenThen
    with Serdes {

  val PlayerKpiStoreName: String = "test-player-kpi-store"
  var processorContext: MockProcessorContext[String, PlayerKpi] = _
  var processor: PlayerKpiProcessor = _
  var playerKpiStore: TimestampedKeyValueStore[String, PlayerKpi] = _

  override def beforeEach(): Unit = {
    processorContext = new MockProcessorContext()

    playerKpiStore = Stores
      .timestampedKeyValueStoreBuilder(
        Stores.inMemoryKeyValueStore(PlayerKpiStoreName),
        stringSerde,
        playerKpiSerde
      )
      .withLoggingDisabled // Logging must be disabled with MockProcessorContext.
      .build

    playerKpiStore.init(processorContext.getStateStoreContext, playerKpiStore)
    processorContext.getStateStoreContext.register(playerKpiStore, (_, _) => {})
    processor = PlayerKpiProcessor(PlayerKpiStoreName)
    processor.init(processorContext)
  }

  override def afterEach(): Unit = {
    processor.close()
    playerKpiStore.close()
  }

  behavior of "process"
  it should "forward updated kpi only" in {
    Given("Exists player kpi in the store")
    playerKpiStore.put(
      "player-id",
      ValueAndTimestamp.make(
        PlayerKpi(
          id = "player-id",
          username = "player-username",
          title = Some("GM"),
          createdAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
          seenAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
          url = "player-url",
          playTime = Some(1000L),
          incorrectMoveCounter = Some(10),
          correctMoveCounter = Some(10),
          correctIncorrectMoveRatio = Some(1.0),
          meanPlayerAccuracy = Some(0.5),
          accuracies = Map(
            "game-id-1" -> 0.5,
            "game-id-2" -> 0.8
          )
        ),
        Instant.now().toEpochMilli
      )
    )
    When("New Game KPI event is generated.")
    val gameKpi = GameKpi(
      id = "player-id",
      username = "player-username",
      gameId = "game-id-2",
      gameStatus = "started",
      brilliantMoveCounter = Some(1),
      excellentMoveCounter = Some(2),
      goodMoveCounter = Some(3),
      inaccuracyMoveCounter = Some(4),
      mistakeMoveCounter = Some(5),
      blunderMoveCounter = Some(1),
      accuracy = Some(0.375)
    )

    When("Player KPI processor is called")
    processor.process(
      record = new Record[String, GameKpi](
        "player-id",
        gameKpi,
        1L
      )
    )
    Then("State store is NOT updated")
    val kpi = playerKpiStore.get("player-id").value()
    kpi.correctMoveCounter should contain(10)
    kpi.incorrectMoveCounter should contain(10)
    kpi.correctIncorrectMoveRatio should contain(1.0)

    And("Updated KPI is forwarded to downstream")
    val forwarded = processorContext.forwarded
    forwarded.size() should be(1)
    forwarded.get(0).record().key() should be("player-id")
    forwarded.get(0).record().value().id should be("player-id")
    forwarded.get(0).record().value().username should be("player-username")
    forwarded.get(0).record().value().correctMoveCounter should be(Some(16))
    forwarded.get(0).record().value().incorrectMoveCounter should be(Some(20))
    forwarded.get(0).record().value().correctIncorrectMoveRatio should be(Some(0.8))
    forwarded.get(0).record().value().meanPlayerAccuracy should be(Some(0.4444444444444444))
    forwarded.get(0).record().value().accuracies should be(
      Map(
        "game-id-1" -> 0.5,
        "game-id-2" -> 0.375
      )
    )
    forwarded.get(0).record().value().macroAccuracy should be(Some(0.4375))
    forwarded.get(0).record().value().accuracySTD should contain(
      math.sqrt(
        (math.pow(0.5 - 0.4444444444444444, 2) + math.pow(0.375 - 0.4444444444444444, 2)) / 2
      )
    )
  }
  it should "forward updated kpi and update state store" in {
    Given("Exists player kpi in the store,")
    playerKpiStore.put(
      "player-id",
      ValueAndTimestamp.make(
        PlayerKpi(
          id = "player-id",
          username = "player-username",
          title = Some("GM"),
          createdAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
          seenAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
          url = "player-url",
          playTime = Some(1000L),
          incorrectMoveCounter = Some(10),
          correctMoveCounter = Some(10),
          correctIncorrectMoveRatio = Some(1.0),
          meanPlayerAccuracy = Some(0.5),
          accuracies = Map(
            "game-id-1" -> 0.5,
            "game-id-2" -> 0.8
          )
        ),
        Instant.now().toEpochMilli
      )
    )

    When("New Game KPI event is generated.")
    And("The game status is not started -> Game is over.")
    val gameKpi = GameKpi(
      id = "player-id",
      username = "player-username",
      gameId = "game-id-2",
      gameStatus = "resign",
      winner = Some("player-id"),
      brilliantMoveCounter = Some(1),
      excellentMoveCounter = Some(2),
      goodMoveCounter = Some(3),
      inaccuracyMoveCounter = Some(4),
      mistakeMoveCounter = Some(5),
      blunderMoveCounter = Some(1),
      accuracy = Some(0.375)
    )

    When("Player KPI processor is called")
    processor.process(
      record = new Record[String, GameKpi](
        "player-id",
        gameKpi,
        1L
      )
    )
    Then("State store is updated")
    val kpi = playerKpiStore.get("player-id").value()
    kpi.id should be("player-id")
    kpi.username should be("player-username")
    kpi.correctMoveCounter should be(Some(16))
    kpi.incorrectMoveCounter should be(Some(20))
    kpi.correctIncorrectMoveRatio should be(Some(0.8))
    kpi.meanPlayerAccuracy should be(Some(0.4444444444444444))
    kpi.accuracies should be(
      Map(
        "game-id-1" -> 0.5,
        "game-id-2" -> 0.375
      )
    )
    kpi.macroAccuracy should be(Some(0.4375))
    kpi.accuracySTD should contain(
      math.sqrt(
        (math.pow(0.5 - 0.4444444444444444, 2) + math.pow(0.375 - 0.4444444444444444, 2)) / 2
      )
    )

    And("Updated KPI is forwarded to downstream")
    val forwarded = processorContext.forwarded
    forwarded.size() should be(1)
    forwarded.get(0).record().key() should be("player-id")
    forwarded.get(0).record().value() should be(kpi)
  }

  behavior of "updatePlayerKpi"
  it should "update players kpi with incoming kpi for existing game" in {
    val playerKpi = PlayerKpi(
      id = "test-player-id",
      username = "test-player-username",
      title = Some("GM"),
      createdAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
      seenAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
      url = "test-player-url",
      playTime = Some(1000L),
      incorrectMoveCounter = Some(10),
      correctMoveCounter = Some(10),
      correctIncorrectMoveRatio = Some(1.0),
      meanPlayerAccuracy = Some(0.5),
      accuracies = Map(
        "test-game-id-1" -> 0.5,
        "test-game-id-2" -> 0.8
      )
    )
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

    val updatedKpi = processor.updatePlayerKpi(playerKpi, gameKpi)
    updatedKpi.id should be("test-player-id")
    updatedKpi.username should be("test-player-username")
    updatedKpi.title should be(Some("GM"))
    updatedKpi.createdAt should be(Instant.parse("2021-01-01T00:00:00Z").toEpochMilli)
    updatedKpi.seenAt should be(Instant.parse("2021-01-01T00:00:00Z").toEpochMilli)
    updatedKpi.url should be("test-player-url")
    updatedKpi.playTime should be(Some(1000L))
    updatedKpi.incorrectMoveCounter should be(Some(20))
    updatedKpi.correctMoveCounter should be(Some(16))
    updatedKpi.correctIncorrectMoveRatio should be(Some(0.8))
    updatedKpi.meanPlayerAccuracy should be(Some(0.4444444444444444))
    updatedKpi.accuracies should be(
      Map(
        "test-game-id-1" -> 0.5,
        "test-game-id-2" -> 0.375
      )
    )
    updatedKpi.macroAccuracy should be(Some(0.4375))
    updatedKpi.accuracySTD should contain(
      math.sqrt(
        (math.pow(0.5 - 0.4444444444444444, 2) + math.pow(0.375 - 0.4444444444444444, 2)) / 2
      )
    )

  }

  behavior of "updateGameCount"
  it should "update game counters when game is finished" in {
    val playerKpi = PlayerKpi(
      id = "test-player-id",
      username = "test-player-username",
      createdAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
      seenAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
      url = "test-player-url",
      playTime = Some(1000L),
      accuracies = Map(
        "test-game-id-1" -> 0.5,
        "test-game-id-2" -> 0.8
      ),
      gameCount = Some(
        count(
          all = Some(4L),
          rated = Some(1L),
          win = Some(2L),
          draw = None,
          loss = Some(1L)
        )
      )
    )
    val gameKpi = GameKpi(
      id = "test-player-id",
      username = "test-player-username",
      gameId = "test-game-id-2",
      gameStatus = "resign",
      winner = Some("test-player-id"),
      rated = Some(false)
    )

    val gameCount = processor.updateGameCount(playerKpi, gameKpi)
    gameCount.all should be(Some(5L))
    gameCount.rated should contain(1L)
    gameCount.win should contain(3L)
    gameCount.draw should be(None)
    gameCount.loss should contain(1L)
  }

  behavior of "updateWinLossRatio"
  it should "calculate win/loss ration when player has games" in {
    val kpi = PlayerKpi(
      id = "test-player-id",
      username = "test-player-username",
      createdAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
      seenAt = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli,
      url = "test-player-url",
      playTime = Some(1000L),
      accuracies = Map(
        "test-game-id-1" -> 0.5,
        "test-game-id-2" -> 0.8
      ),
      gameCount = Some(
        count(
          all = Some(8L),
          rated = Some(1L),
          win = Some(5L),
          draw = None,
          loss = Some(2L)
        )
      )
    )

    processor.updateWinLossRation(kpi) should contain(2.5)
  }
}
