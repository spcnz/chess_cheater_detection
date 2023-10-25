package yauza.chess.processor

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.processor.api.{Processor, ProcessorContext, Record}
import org.apache.kafka.streams.state.KeyValueStore
import yauza.avro.message.chess._
import yauza.chess.`enum`.PlayerColor

case class GameScoreProcessor(gameScoreStoreName: String)
    extends Processor[
      String,
      GameWithMoveScore,
      String,
      PlayerMove
    ]
    with LazyLogging {

  private val moveLabels: Map[Range, MoveLabel] = Map(
    Range.inclusive(-Int.MaxValue, -200) -> MoveLabel.Blunder,
    Range.inclusive(-199, -100) -> MoveLabel.Mistake,
    Range.inclusive(-99, -50) -> MoveLabel.Inaccuracy,
    Range.inclusive(-49, -1) -> MoveLabel.Neutral,
    Range.inclusive(0, 49) -> MoveLabel.Neutral,
    Range.inclusive(50, 99) -> MoveLabel.Good,
    Range.inclusive(100, 199) -> MoveLabel.Excellent,
    Range.inclusive(200, Int.MaxValue) -> MoveLabel.Brilliant
  )
  private var context: ProcessorContext[String, PlayerMove] = _
  private var gameScoreStore: KeyValueStore[String, GameScore] = _

  override def init(processorContext: ProcessorContext[String, PlayerMove]): Unit = {
    context = processorContext

    gameScoreStore = processorContext
      .getStateStore(gameScoreStoreName)
      .asInstanceOf[KeyValueStore[String, GameScore]]

  }

  override def process(
      record: Record[String, GameWithMoveScore]
  ): Unit = {
    val gameWithMoveScore = record.value
    val score: GameScore = Option(gameScoreStore.get(gameWithMoveScore.id))
      .getOrElse {
        logger.warn(
          s"Game score for game ${gameWithMoveScore.id} not found. Initializing with default score."
        )
        initialGameScore(gameWithMoveScore)
      }

    val (id: String, username: String, previousScore: Long) =
      getPlayerInfo(score, gameWithMoveScore.playerColor)

    updateGameScore(score, gameWithMoveScore)
    context.forward(
      record
        .withKey(id)
        .withValue(
          PlayerMove(
            id = id,
            username = username,
            gameId = score.id,
            lastMove = gameWithMoveScore.lastMove,
            label = getLabel(previousScore, gameWithMoveScore.playerScore),
            score = gameWithMoveScore.playerScore
          )
        )
    )
    logger.info(s"Produced player score update for player: $id")
  }

  def updateGameScore(game: GameScore, move: GameWithMoveScore): Unit = {
    if (move.playerColor == PlayerColor.White) {
      game.whiteScore = move.playerScore
    } else {
      game.blackScore = move.playerScore
    }
    game.fen = move.fen
    game.lastMove = move.lastMove
    gameScoreStore.put(game.id, game)
  }

  def getLabel(previousScore: Long, newScore: Long): MoveLabel = {
    val delta: Long = newScore - previousScore
    moveLabels
      .find { case (range, _) => range.contains(delta) }
      .map { case (_, label) => label }
      .get
  }

  def getPlayerInfo(game: GameScore, color: String): (String, String, Long) =
    if (color == PlayerColor.White) {
      (
        game.players.white.user.id,
        game.players.white.user.name,
        game.whiteScore
      )
    } else {
      (
        game.players.black.user.id,
        game.players.black.user.name,
        game.blackScore
      )
    }

  def initialGameScore(gameWithMoveScore: GameWithMoveScore): GameScore =
    GameScore(
      id = gameWithMoveScore.id,
      whiteScore = 0,
      blackScore = 0,
      fen = gameWithMoveScore.fen,
      lastMove = gameWithMoveScore.lastMove,
      rated = gameWithMoveScore.rated,
      variant = gameWithMoveScore.variant,
      speed = gameWithMoveScore.speed,
      perf = gameWithMoveScore.perf,
      createdAt = gameWithMoveScore.createdAt,
      lastMoveAt = gameWithMoveScore.lastMoveAt,
      status = gameWithMoveScore.status,
      players = gameWithMoveScore.players,
      moves = gameWithMoveScore.moves,
      clock = gameWithMoveScore.clock
    )

  override def close(): Unit = {}
}
