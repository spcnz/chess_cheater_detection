package yauza.chess.aggregator

import yauza.avro.message.chess.{MoveLabel, PlayerGameKpi, PlayerMove}

trait PlayerGameKpiAggregator {

  def aggregate(key: String, playerMove: PlayerMove, kpi: PlayerGameKpi): PlayerGameKpi = {
    val newKpi = PlayerGameKpi(
      id = playerMove.id,
      username = playerMove.username,
      gameId = playerMove.gameId,
      brilliantMoveCounter =
        Some(updatedCounter(playerMove.label, kpi.brilliantMoveCounter, MoveLabel.Brilliant)),
      excellentMoveCounter = Some(
        updatedCounter(playerMove.label, kpi.excellentMoveCounter, MoveLabel.Excellent)
      ),
      goodMoveCounter = Some(updatedCounter(playerMove.label, kpi.goodMoveCounter, MoveLabel.Good)),
      inaccuracyMoveCounter = Some(
        updatedCounter(playerMove.label, kpi.inaccuracyMoveCounter, MoveLabel.Inaccuracy)
      ),
      mistakeMoveCounter = Some(
        updatedCounter(playerMove.label, kpi.mistakeMoveCounter, MoveLabel.Mistake)
      ),
      blunderMoveCounter = Some(
        updatedCounter(playerMove.label, kpi.blunderMoveCounter, MoveLabel.Blunder)
      )
    )
    // ovde videti sta cu ako je nula imenliac
    newKpi.accuracy = Some(calcAccuracy(newKpi))

    newKpi
  }

  private def updatedCounter(
      moveLabel: MoveLabel,
      counter: Option[Long],
      counterLabel: MoveLabel
  ): Long =
    counter.getOrElse(0L) + (if (moveLabel == counterLabel) 1 else 0)

  private def calcAccuracy(kpi: PlayerGameKpi): Double =
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

  def merge(key: String, previousKpi: PlayerGameKpi, newKpi: PlayerGameKpi): PlayerGameKpi =
    PlayerGameKpi(
      id = newKpi.id,
      username = newKpi.username,
      gameId = newKpi.gameId,
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
      ),
      accuracy = newKpi.accuracy
    )

}
