package yauza.chess

import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry
import org.apache.kafka.streams.{Topology, TopologyTestDriver}

import java.util.Properties
import scala.reflect.io.{Directory, Path}

trait TopologyTest extends UnitTest {

  def topology: Topology
  def properties: Properties
  def schemaRegistryScope: String
  def stateDir: Option[Path] = None

  def createInputTopics(): Unit = {}
  def createInternalTopics(): Unit = {}
  def createOutputTopics(): Unit = {}

  protected var driver: TopologyTestDriver = _

  override def beforeEach(): Unit = {
    MockSchemaRegistry.getClientForScope(schemaRegistryScope)
    driver = new TopologyTestDriver(topology, properties)

    createInputTopics()
    createInternalTopics()
    createOutputTopics()
  }

  override def afterEach(): Unit = {
    MockSchemaRegistry.dropScope(schemaRegistryScope)
    driver.close()
  }

  override def afterAll(): Unit =
    deleteStateDir()

  private def deleteStateDir(): Unit =
    stateDir match {
      case Some(path) => Directory(path).deleteRecursively()
      case None       =>
    }

}
