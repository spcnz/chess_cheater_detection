package yauza

import yauza.config.Configuration
import yauza.producer.{GameProducer, GameResultProducer, MoveProducer, PlayerProducer}
import picocli.CommandLine
import picocli.CommandLine.{Command, Option}
import yauza.api.{Api, LichessApi}
import io.circe.jawn.decode
import io.circe.generic.auto._
import yauza.avro.message.chess.GameResult

import java.util.concurrent.Callable

@Command(
  name = "Runner",
  mixinStandardHelpOptions = true,
  version = Array("1.0"),
  description = Array("Produce data from Lichess API")
)
object Runner extends Callable[Int] {

  val SupportedDatasets: Set[String] = Set(
    "games",
    "moves",
    "players",
    "game-results"
  )

  @Option(
    names = Array("-d", "--dataset"),
    `type` = Array(classOf[String]),
    description = Array(
      "Specifies which dataset should be fetch from API. Valid values are: [" +
        "games" +
        "moves, " +
        "players, " +
        "game-results, " +
        "]."
    )
  )
  private var dataset: String = ""

  private def run(): Unit = {
    implicit val config: Configuration = Configuration()
    val api: Api = LichessApi()
    dataset match {
      case "games"        => GameProducer(api).produce()
      case "moves"        => MoveProducer(api).produce()
      case "players"      => PlayerProducer(api).produce()
      case "game-results" => GameResultProducer(api).produce()
      case _              => println(s"Not valid option : ${dataset} for dataset argument.")
    }
  }

  def main(args: Array[String]): Unit = {
    val code = new CommandLine(this).execute(args: _*)
    System.exit(code)
  }

  override def call(): Int = {
    run()
    0
  }
}
