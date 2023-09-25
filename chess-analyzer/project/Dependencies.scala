import sbt._

object Dependencies {
  val streamsCommonVersion = "2.0.0"
  val kafkaStreamsVersion = "7.4.0"
  val scalaTestVersion = "3.2.12"
  val mockitoVersion = "1.16.0"

  val kafkaStreamsCommunityVersion: String = kafkaStreamsVersion + "-ce"

  // format: off
  val all: Seq[ModuleID] = Seq(
    "gaia" %% "skyline-kafka-streams-common" % streamsCommonVersion,
    "org.apache.kafka" % "kafka-streams-test-utils" % kafkaStreamsCommunityVersion % Test,
    "io.confluent" % "kafka-schema-registry-client" % kafkaStreamsVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.mockito" %% "mockito-scala" % mockitoVersion % Test

  )
}
