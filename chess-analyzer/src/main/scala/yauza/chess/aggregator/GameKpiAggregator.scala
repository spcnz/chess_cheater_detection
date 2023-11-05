package yauza.chess.aggregator

import yauza.avro.message.chess.{GameKpi, MoveLabel, PlayerMove}

trait GameKpiAggregator {

  def aggregate(key: String, playerMove: PlayerMove, kpi: GameKpi): GameKpi = {
    val newKpi = GameKpi(
      id = playerMove.id,
      username = playerMove.username,
      gameId = playerMove.gameId,
      gameStatus = playerMove.gameStatus,
      brilliantMoveCounter =
        Some(incrementCount(playerMove.label, kpi.brilliantMoveCounter, MoveLabel.Brilliant)),
      excellentMoveCounter = Some(
        incrementCount(playerMove.label, kpi.excellentMoveCounter, MoveLabel.Excellent)
      ),
      goodMoveCounter = Some(incrementCount(playerMove.label, kpi.goodMoveCounter, MoveLabel.Good)),
      inaccuracyMoveCounter = Some(
        incrementCount(playerMove.label, kpi.inaccuracyMoveCounter, MoveLabel.Inaccuracy)
      ),
      mistakeMoveCounter = Some(
        incrementCount(playerMove.label, kpi.mistakeMoveCounter, MoveLabel.Mistake)
      ),
      blunderMoveCounter = Some(
        incrementCount(playerMove.label, kpi.blunderMoveCounter, MoveLabel.Blunder)
      )
    )
    newKpi.accuracy = Some(calcAccuracy(newKpi))

    newKpi
  }

  protected def incrementCount(
      moveLabel: MoveLabel,
      counterValue: Option[Long],
      counterLabel: MoveLabel
  ): Long =
    counterValue.getOrElse(0L) + (if (moveLabel == counterLabel) 1 else 0)

  def merge(key: String, previousKpi: GameKpi, newKpi: GameKpi): GameKpi = {
    val mergedKpi = GameKpi(
      id = newKpi.id,
      username = newKpi.username,
      gameId = newKpi.gameId,
      gameStatus = newKpi.gameStatus,
      brilliantMoveCounter = Some(
        previousKpi.brilliantMoveCounter.getOrElse(0L) + newKpi.brilliantMoveCounter
          .getOrElse(0L)
      ),
      excellentMoveCounter = Some(
        previousKpi.excellentMoveCounter.getOrElse(0L) + newKpi.excellentMoveCounter
          .getOrElse(0L)
      ),
      goodMoveCounter = Some(
        previousKpi.goodMoveCounter.getOrElse(0L) + newKpi.goodMoveCounter
          .getOrElse(0L)
      ),
      inaccuracyMoveCounter = Some(
        previousKpi.inaccuracyMoveCounter.getOrElse(0L) + newKpi.inaccuracyMoveCounter
          .getOrElse(0L)
      ),
      mistakeMoveCounter = Some(
        previousKpi.mistakeMoveCounter.getOrElse(0L) + newKpi.mistakeMoveCounter
          .getOrElse(0L)
      ),
      blunderMoveCounter = Some(
        previousKpi.blunderMoveCounter.getOrElse(0L) + newKpi.blunderMoveCounter
          .getOrElse(0L)
      )
    )
    mergedKpi.accuracy = Some(calcAccuracy(mergedKpi))
    mergedKpi
  }

  protected def calcAccuracy(kpi: GameKpi): Double =
    Seq(
      kpi.brilliantMoveCounter,
      kpi.goodMoveCounter,
      kpi.excellentMoveCounter
    ).map(_.getOrElse(0L)).sum.toDouble / Seq(
      kpi.brilliantMoveCounter,
      kpi.goodMoveCounter,
      kpi.excellentMoveCounter,
      kpi.inaccuracyMoveCounter,
      kpi.mistakeMoveCounter,
      kpi.blunderMoveCounter
    ).map(_.getOrElse(0L)).sum.toDouble
}
