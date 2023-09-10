package yauza.chess

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.KafkaStreams
import yauza.chess.engine.{Engine, StockfishEngine}

object Application extends LazyLogging {

  def main(args: Array[String]): Unit = {
    val engine: Engine = StockfishEngine()
    val topology = TopologyBuilder(engine).build

    logger.info(topology.describe.toString)

    val app = new KafkaStreams(topology, config.streamProperties)
    engine.start()
    app.start()

    // Gracefully close Stream app on SIGINT.
    sys.addShutdownHook {
      logger.info("Shutting down Stream app...")
      app.close()
      engine.close()
    }
  }
}
