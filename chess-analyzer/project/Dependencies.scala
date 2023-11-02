import sbt.*

object Dependencies {
  val kafkaStreamsVersion = "7.4.0"

  val avroVersion = "1.11.0"
  val logbackVersion = "1.2.11"
  val pureConfigVersion = "0.17.1"
  val scalaLoggingVersion = "3.9.5"

  val scalaTestVersion = "3.2.12"
  val mockitoVersion = "1.16.0"

  val kafkaStreamsCommunityVersion: String = kafkaStreamsVersion + "-ce"



  // format: off
  val all: Seq[ModuleID] = Seq(
    "org.apache.avro" % "avro" % avroVersion,
    "io.confluent" % "kafka-streams-avro-serde" % kafkaStreamsVersion,
    "org.apache.kafka" % "kafka-clients" % kafkaStreamsCommunityVersion,
    "org.apache.kafka" %% "kafka-streams-scala" % kafkaStreamsCommunityVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "com.github.pureconfig" %% "pureconfig" % pureConfigVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.kafka" % "kafka-streams-test-utils" % kafkaStreamsCommunityVersion % Test,
    "io.confluent" % "kafka-schema-registry-client" % kafkaStreamsVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.mockito" %% "mockito-scala" % mockitoVersion % Test
  )
}