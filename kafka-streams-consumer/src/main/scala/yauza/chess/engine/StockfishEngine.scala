package yauza.chess.engine

import com.typesafe.scalalogging.LazyLogging
import yauza.chess.{PlayerColor, ScoreType}

import java.io.{InputStream, OutputStream, OutputStreamWriter}
import scala.io.Source
import scala.sys.process._
import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

case class StockfishEngine(
    depth: Int = 1,
    searchTimeMillis: Int = 1000
) extends Engine
    with LazyLogging {

  val centipawnPrefix: String = "cp "
  val centipawnScorePattern: Regex = (centipawnPrefix + "[-+]?[0-9]+").r

  val matePrefix: String = "mate "
  val mateScorePattern: Regex = (matePrefix + "[-+]?[0-9]+").r

  override def start(): Unit = {
    process = Process(yauza.chess.config.chessAnalyzer.engine.path).run(
      new ProcessIO(
        writeInput = (stdout: OutputStream) => inputStream = new OutputStreamWriter(stdout),
        processOutput = (stdin: InputStream) => outputStream = stdin,
        processError = (_: InputStream) => (),
        daemonizeThreads = true
      )
    )
    inputStream.write(s"uci\n")
    inputStream.flush()

    Source
      .fromInputStream(outputStream)
      .getLines()
      .takeWhile(_ != "uciok")
      .mkString("\n")
  }

  override private[engine] def setPosition(fen: String): Unit =
    inputStream.write(s"position fen ${fen}\n")

  override def getPointOfView(fen: String): String =
    Try(fen.split(" ")(1)) match {
      case Success(color) =>
        color match {
          case PlayerColor.White => PlayerColor.White
          case PlayerColor.Black => PlayerColor.Black
          case _                 => throw new RuntimeException(s"Invalid color: $color")
        }
      case Failure(_) => throw new RuntimeException(s"No player color in fen: $fen")
    }

  override def getPlayerScore(fen: String): (Long, String) = {
    setPosition(fen)
    inputStream.write(s"go depth ${depth} movetime ${searchTimeMillis}\n")
    inputStream.flush()

    val scoreInfo = scala.io.Source
      .fromInputStream(outputStream)
      .getLines()
      .find(_.startsWith(s"info depth $depth"))
      .getOrElse(throw new RuntimeException("No score information found."))

    val centipawnScore: Option[Long] = extractScore(
      scoreInfo,
      centipawnScorePattern,
      centipawnPrefix.length,
      ScoreType.Centipawn
    )

    val mateScore: Option[Long] =
      if (centipawnScore.isEmpty)
        extractScore(
          scoreInfo,
          mateScorePattern,
          matePrefix.length,
          ScoreType.Mate
        )
      else None

    (
      centipawnScore.getOrElse(mateScore.getOrElse {
        throw new RuntimeException(s"No score found. Score info: $scoreInfo. Fen: $fen")
      }),
      centipawnScore.map(_ => ScoreType.Centipawn).getOrElse(ScoreType.Mate)
    )
  }

  private def extractScore(
      info: String,
      pattern: Regex,
      prefixLength: Int,
      scoreType: String
  ): Option[Long] =
    pattern
      .findFirstIn(info)
      .map(_.substring(prefixLength).toLong)
      .orElse {
        logger.info(s"No $scoreType score found. Score info: $info")
        None
      }

  override def close(): Unit = {
    inputStream.close()
    outputStream.close()
    process.destroy()
  }
}
