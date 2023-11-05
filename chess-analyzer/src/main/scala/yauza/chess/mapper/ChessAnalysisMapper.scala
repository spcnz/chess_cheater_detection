package yauza.chess.mapper

import yauza.avro.message.chess._

trait ChessAnalysisMapper {
  def mapMove(score: Long, scoreType: String, playerColor: String): Move => MoveWithScore =
    (move: Move) =>
      MoveWithScore(
        gameId = move.gameId,
        fen = move.fen,
        lm = move.lm,
        playerScore = score,
        playerColor = playerColor,
        moveIndex = move.moveIndex,
        scoreType = scoreType
      )

  def mapPlayerKpi: Player => PlayerKpi =
    (player: Player) =>
      PlayerKpi(
        id = player.id,
        username = player.username,
        bulletGameStatistic = player.perfs.bullet,
        title = player.title,
        createdAt = player.createdAt,
        country = player.profile.flatMap(_.country),
        seenAt = player.seenAt,
        playTime = Some(player.playTime.total),
        url = player.url,
        gameCount = player.count,
        winLossRatio = player.count match {
          case Some(count) =>
            Some(count.win.map(_.toDouble).getOrElse(0.0) / count.loss.getOrElse(1L))
          case _ => None
        }
      )

}
