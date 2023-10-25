package yauza.chess.aggregator

import yauza.avro.message.chess.{MoveLabel, PlayerGameKpi, PlayerMove}
import yauza.chess.UnitTest

class PlayerGameKpiAggregatorTest extends UnitTest with PlayerGameKpiAggregator {

  behavior of "aggregate"
  it should "update existing KPI aggregations when new move arrives" in {
    val kpi = PlayerGameKpi(
      id = "1",
      username = "1",
      gameId = "1",
      brilliantMoveCounter = Some(0L),
      excellentMoveCounter = Some(0L),
      goodMoveCounter = Some(3L),
      inaccuracyMoveCounter = Some(2L),
      mistakeMoveCounter = Some(10L),
      blunderMoveCounter = Some(0L)
    )
    val move = PlayerMove(
      id = "1",
      username = "1",
      gameId = "1",
      label = MoveLabel.Good,
      score = 200,
      lastMove = "a2a4"
    )

    aggregate("1", move, kpi) should be(
      PlayerGameKpi(
        id = "1",
        username = "1",
        gameId = "1",
        brilliantMoveCounter = Some(0L),
        excellentMoveCounter = Some(0L),
        goodMoveCounter = Some(4L),
        inaccuracyMoveCounter = Some(2L),
        mistakeMoveCounter = Some(10L),
        blunderMoveCounter = Some(0L),
        accuracy = Some(0.25)
      )
    )
  }

  behavior of "incrementCount"
  it should "increment good move count" in {
    incrementCount(
      moveLabel = MoveLabel.Good,
      counterValue = Some(2L),
      counterLabel = MoveLabel.Good
    ) should be(3L)
  }
  it should "preserve old value when labels don't match" in {
    incrementCount(
      moveLabel = MoveLabel.Excellent,
      counterValue = Some(2L),
      counterLabel = MoveLabel.Good
    ) should be(2L)
  }

  behavior of "calcAccuracy"
  it should "return ratio of good and bad moves" in {
    val kpi = PlayerGameKpi(
      id = "1",
      username = "1",
      gameId = "1",
      brilliantMoveCounter = Some(1L),
      excellentMoveCounter = Some(0L),
      goodMoveCounter = Some(3L),
      inaccuracyMoveCounter = Some(2L),
      mistakeMoveCounter = Some(10L),
      blunderMoveCounter = Some(0L)
    )

    calcAccuracy(kpi) should be(0.25)
  }
  it should "handle division by zero" in {
    val kpi = PlayerGameKpi(
      id = "1",
      username = "1",
      gameId = "1",
      brilliantMoveCounter = Some(0L),
      excellentMoveCounter = Some(0L),
      goodMoveCounter = Some(0L),
      inaccuracyMoveCounter = Some(0L),
      mistakeMoveCounter = Some(0L),
      blunderMoveCounter = Some(0L)
    )

    calcAccuracy(kpi).isNaN should be(true)
  }

  behavior of "merge"
  it should "merge two KPI when session window is merging" in {
    val kpiFirstSession = PlayerGameKpi(
      id = "1",
      username = "1",
      gameId = "1",
      brilliantMoveCounter = Some(0L),
      excellentMoveCounter = Some(0L),
      goodMoveCounter = Some(3L),
      inaccuracyMoveCounter = Some(2L),
      mistakeMoveCounter = Some(10L),
      blunderMoveCounter = Some(0L)
    )

    val kpiSecondSession = PlayerGameKpi(
      id = "1",
      username = "1",
      gameId = "1",
      brilliantMoveCounter = Some(1L),
      excellentMoveCounter = Some(0L),
      goodMoveCounter = Some(3L),
      inaccuracyMoveCounter = Some(0L),
      mistakeMoveCounter = Some(0L),
      blunderMoveCounter = Some(0L)
    )

    merge("1", kpiFirstSession, kpiSecondSession) should be(
      PlayerGameKpi(
        id = "1",
        username = "1",
        gameId = "1",
        brilliantMoveCounter = Some(1L),
        excellentMoveCounter = Some(0L),
        goodMoveCounter = Some(6L),
        inaccuracyMoveCounter = Some(2L),
        mistakeMoveCounter = Some(10L),
        blunderMoveCounter = Some(0L),
        accuracy = Some(7 / 19.0)
      )
    )
  }
}
