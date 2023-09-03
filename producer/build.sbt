import gaia.sbt.avrogen.Schema

ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "producer",
    resolvers ++= Seq(
      "confluent" at "https://packages.confluent.io/maven/"
    ),
    libraryDependencies ++= Dependencies.all,
    resolvers ++= Seq("confluent".at("https://packages.confluent.io/maven/")),
    Compile / avroSchemaRegistryUrl := sys.props("schema.registry.url"),
    Compile / avroSchemas := Seq(
      Schema("yauza.avro.message.chess.Move"),
      Schema("yauza.avro.message.chess.GamePlayer"),
      Schema("yauza.avro.message.chess.GameUser"),
      Schema("yauza.avro.message.chess.GamePlayers"),
      Schema("yauza.avro.message.chess.GameClock"),
      Schema("yauza.avro.message.chess.Game"),
      Schema("yauza.avro.message.chess.GameResult"),
      Schema("yauza.avro.message.chess.GameResultVariant"),
      Schema("yauza.avro.message.chess.GameResultStatus"),
      Schema("yauza.avro.message.chess.PuzzleStatistic"),
      Schema("yauza.avro.message.chess.GameStatistic"),
      Schema("yauza.avro.message.chess.Player")
    ),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) =>
        xs map {
          _.toLowerCase
        } match {
          case "services" :: xs =>
            MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.discard
        }

      case _ => MergeStrategy.first
    },
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
    assembly / test := {}
  )

scalafmtOnCompile := true
