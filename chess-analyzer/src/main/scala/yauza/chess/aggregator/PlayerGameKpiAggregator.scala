package yauza.chess.aggregator

import yauza.avro.message.chess.{MoveLabel, PlayerGameKpi, PlayerMove}

trait PlayerGameKpiAggregator {

  def aggregate(key: String, playerMove: PlayerMove, kpi: PlayerGameKpi): PlayerGameKpi = {
    val newKpi = PlayerGameKpi(
      id = playerMove.id,
      username = playerMove.username,
      gameId = playerMove.gameId,
      brilliant_move_counter =
        Some(updatedCounter(playerMove.label, kpi.brilliant_move_counter, MoveLabel.Brilliant)),
      excellent_move_counter = Some(
        updatedCounter(playerMove.label, kpi.excellent_move_counter, MoveLabel.Excellent)
      ),
      good_move_counter =
        Some(updatedCounter(playerMove.label, kpi.good_move_counter, MoveLabel.Good)),
      inaccuracy_move_counter = Some(
        updatedCounter(playerMove.label, kpi.inaccuracy_move_counter, MoveLabel.Inaccuracy)
      ),
      mistake_move_counter = Some(
        updatedCounter(playerMove.label, kpi.mistake_move_counter, MoveLabel.Mistake)
      ),
      blunder_move_counter = Some(
        updatedCounter(playerMove.label, kpi.blunder_move_counter, MoveLabel.Blunder)
      )
    )
    // ovde videti sta cu ako je nula imenliac
    newKpi.accuracy = Some(calcAccuracy(kpi))

    newKpi
  }
  def merge(key: String, previousKpi: PlayerGameKpi, newKpi: PlayerGameKpi): PlayerGameKpi =
    PlayerGameKpi(
      id = newKpi.id,
      username = newKpi.username,
      gameId = newKpi.gameId,
      brilliant_move_counter = Some(
        previousKpi.brilliant_move_counter.getOrElse(0L) + newKpi.brilliant_move_counter
          .getOrElse(0L)
      ),
      excellent_move_counter = Some(
        previousKpi.excellent_move_counter.getOrElse(0L) + newKpi.excellent_move_counter
          .getOrElse(0L)
      ),
      good_move_counter = Some(
        previousKpi.good_move_counter.getOrElse(0L) + newKpi.good_move_counter
          .getOrElse(0L)
      ),
      inaccuracy_move_counter = Some(
        previousKpi.inaccuracy_move_counter.getOrElse(0L) + newKpi.inaccuracy_move_counter
          .getOrElse(0L)
      ),
      mistake_move_counter = Some(
        previousKpi.mistake_move_counter.getOrElse(0L) + newKpi.mistake_move_counter
          .getOrElse(0L)
      ),
      blunder_move_counter = Some(
        previousKpi.blunder_move_counter.getOrElse(0L) + newKpi.blunder_move_counter
          .getOrElse(0L)
      ),
      accuracy = newKpi.accuracy
    )

  private def updatedCounter(
      moveLabel: MoveLabel,
      counter: Option[Long],
      counterLabel: MoveLabel
  ): Long =
    counter.getOrElse(0L) + (if (moveLabel == counterLabel) 1 else 0)

  private def calcAccuracy(kpi: PlayerGameKpi): Double =
    Seq(
      kpi.brilliant_move_counter,
      kpi.good_move_counter,
      kpi.excellent_move_counter
    ).map(_.getOrElse(0L)).sum.toDouble / Seq(
      kpi.brilliant_move_counter,
      kpi.good_move_counter,
      kpi.excellent_move_counter,
      kpi.inaccuracy_move_counter,
      kpi.mistake_move_counter,
      kpi.blunder_move_counter
    ).map(_.getOrElse(0L)).sum.toDouble

}
