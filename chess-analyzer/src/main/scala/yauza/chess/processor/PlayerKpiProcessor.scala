package yauza.chess.processor

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.processor.api.{Processor, ProcessorContext, Record}
import org.apache.kafka.streams.state.{TimestampedKeyValueStore, ValueAndTimestamp}
import yauza.avro.message.chess.{count, GameKpi, PlayerKpi}

import java.time.Instant

case class PlayerKpiProcessor(playerKpiStoreName: String)
    extends Processor[
      String,
      GameKpi,
      String,
      PlayerKpi
    ]
    with LazyLogging {

  private var context: ProcessorContext[String, PlayerKpi] = _
  private var playerKpiStore: TimestampedKeyValueStore[String, PlayerKpi] = _

  override def init(processorContext: ProcessorContext[String, PlayerKpi]): Unit = {
    context = processorContext

    playerKpiStore = processorContext
      .getStateStore(playerKpiStoreName)
      .asInstanceOf[TimestampedKeyValueStore[String, PlayerKpi]]

  }

  override def process(
      record: Record[String, GameKpi]
  ): Unit = {
    val gameKpi = record.value

    val playerKpi: PlayerKpi = playerKpiStore.get(gameKpi.id).value()
    if (playerKpi != null) {

      val updatedKpi = updatePlayerKpi(playerKpi, gameKpi)

      if (gameKpi.gameStatus != "started") {
        // update player KPI store
        updatedKpi.gameCount = Some(updateGameCount(updatedKpi, gameKpi))
        updatedKpi.winLossRatio = updateWinLossRation(updatedKpi)

        playerKpiStore.put(
          updatedKpi.id,
          ValueAndTimestamp.make(updatedKpi, Instant.now().toEpochMilli)
        )
      }
      context.forward(
        record
          .withKey(updatedKpi.id)
          .withValue(
            updatedKpi
          )
      )
      logger.info(s"Produced player KPI update for player: ${updatedKpi.id}")
    } else {
      logger.warn(
        s"Player KPI for player ${gameKpi.id} not found in playerKpiStore."
      )
    }
  }

  def updatePlayerKpi(playerKpi: PlayerKpi, gameKpi: GameKpi): PlayerKpi = {
    val updatedKpi = playerKpi.copy(
      incorrectMoveCounter = Some(
        playerKpi.incorrectMoveCounter.getOrElse(0L)
          + gameKpi.inaccuracyMoveCounter.getOrElse(0L)
          + gameKpi.mistakeMoveCounter.getOrElse(0L)
          + gameKpi.blunderMoveCounter.getOrElse(0L)
      ),
      correctMoveCounter = Some(
        playerKpi.correctMoveCounter.getOrElse(0L)
          + gameKpi.goodMoveCounter.getOrElse(0L)
          + gameKpi.excellentMoveCounter.getOrElse(0L)
          + gameKpi.brilliantMoveCounter.getOrElse(0L)
      )
    )
    // update correct incorrect ratio
    updatedKpi.correctIncorrectMoveRatio = Some(
      updatedKpi.correctMoveCounter.map(_.toDouble).getOrElse(0.0)
        /
          updatedKpi.incorrectMoveCounter
            .map(_.toDouble)
            .getOrElse(1.0)
    )
    // update mean accuracy
    updatedKpi.meanPlayerAccuracy = Some(
      updatedKpi.correctMoveCounter.map(_.toDouble).getOrElse(0.0) /
        (updatedKpi.correctMoveCounter
          .map(_.toDouble)
          .getOrElse(0.0) + updatedKpi.incorrectMoveCounter.map(_.toDouble).getOrElse(0.0))
    )

    // add/update game accuracy in the accuracy map
    if (gameKpi.accuracy.isDefined)
      updatedKpi.accuracies += (gameKpi.gameId -> gameKpi.accuracy.get)
    updatedKpi.macroAccuracy = Some(updatedKpi.accuracies.values.sum / updatedKpi.accuracies.size)

    // update accuracy standard deviation (STD)
    updatedKpi.accuracySTD = Some(
      math.sqrt(
        updatedKpi.accuracies.values
          .map(acc => math.pow(acc - updatedKpi.meanPlayerAccuracy.getOrElse(0.0), 2))
          .sum / updatedKpi.accuracies.size
      )
    )

    // update median
    updatedKpi.accuracyMedian = Some(
      updatedKpi.accuracies.values.toSeq.sorted.apply(updatedKpi.accuracies.size / 2)
    )

    updatedKpi
  }

  def updateGameCount(kpi: PlayerKpi, gameKpi: GameKpi): count = {
    val gameCount = kpi.gameCount.getOrElse(count())
    gameCount.all = Some(kpi.gameCount.getOrElse(count()).all.getOrElse(0L) + 1L)
    gameCount.rated = gameKpi.rated match {
      case Some(true) => Some(kpi.gameCount.getOrElse(count()).rated.getOrElse(0L) + 1L)
      case _          => gameCount.rated
    }

    if (kpi.id == gameKpi.winner.get) {
      gameCount.win = Some(gameCount.win.getOrElse(0L) + 1L)
    } else {
      if (gameKpi.gameStatus == "draw")
        gameCount.draw = Some(gameCount.draw.getOrElse(0L) + 1L)
      else
        gameCount.loss = Some(gameCount.loss.getOrElse(0L) + 1L)
    }

    gameCount
  }

  def updateWinLossRation(kpi: PlayerKpi): Option[Double] =
    Option(
      kpi.gameCount
        .flatMap(_.win)
        .map(_.toDouble)
        .getOrElse(0.0) / kpi.gameCount
        .flatMap(_.loss)
        .map(_.toDouble)
        .getOrElse(1.0)
    )

}
