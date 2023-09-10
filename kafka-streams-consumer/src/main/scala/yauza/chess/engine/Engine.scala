package yauza.chess.engine
import yauza.avro.message.chess.ScoreType

import java.io.{InputStream, OutputStreamWriter}
import scala.sys.process._

trait Engine {
  var inputStream: OutputStreamWriter = _
  var outputStream: InputStream = _
  var process: Process = _

  def start(): Unit
  private[engine] def setPosition(fen: String): Unit
  def getPointOfView(fen: String): String
  def getPlayerScore(fen: String): (Long, ScoreType)
  def close(): Unit = {
    inputStream.close()
    outputStream.close()
    process.destroy()
  }
}
