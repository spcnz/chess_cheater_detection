import sbt._

object Dependencies {
  val picocliVersion = "4.6.3"
  val pureConfigVersion = "0.15.0"
  val sttpVersion = "3.4.1"
  val kafkaClientsVersion = "3.3.1"
  val circeVersion = "0.14.1"
  val avroSerializerVersion = "5.0.0"
  val akkaHtppBackendVersion = "3.8.5"
  val akkaStreamVersion = "2.7.0"
  val scalaLoggingVersion = "3.9.5"

  val picocli = "info.picocli" % "picocli" % picocliVersion
  val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
  val sttp = "com.softwaremill.sttp.client3" %% "core" % sttpVersion
  val kafkaClients = "org.apache.kafka" % "kafka-clients" % kafkaClientsVersion
  val circeCore = "io.circe" %% "circe-core" % circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  val circeParser = "io.circe" %% "circe-parser" % circeVersion
  val akkaHttpBackend =
    "com.softwaremill.sttp.client3" %% "akka-http-backend" % akkaHtppBackendVersion
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion
  val scalaLogging =
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion

  val avroSerializer = "io.confluent" % "kafka-avro-serializer" % avroSerializerVersion

  val all: Seq[ModuleID] = Seq(
    picocli,
    pureConfig,
    sttp,
    kafkaClients,
    circeCore,
    circeGeneric,
    circeParser,
    akkaHttpBackend,
    akkaStream,
    avroSerializer,
    scalaLogging
  )
}
