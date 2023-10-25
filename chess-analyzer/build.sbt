import gaia.sbt.avrogen.Schema

ThisBuild / scalaVersion := "2.13.11"
ThisBuild / organization := "yauza"
ThisBuild / version := "0.1.0"

lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin, AshScriptPlugin)
  .settings(
    name := "chess-analyzer",
    libraryDependencies ++= Dependencies.all,
    mainClass := Some("yauza.chess.Application"),
    Docker / packageName := "specnazm/chess-analyzer",
    Universal / mappings ++= Seq(
      ((Compile / resourceDirectory).value / s"application.conf") -> "conf/application.conf"
    ),
    dockerUpdateLatest := true,
    dockerBaseImage := "adoptopenjdk/openjdk11:alpine-slim",
    bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf"""",
    Runtime / avroSchemas := Seq(
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
      Schema("yauza.avro.message.chess.Player"),
      Schema("yauza.avro.message.chess.GameScore"),
      Schema("yauza.avro.message.chess.MoveWithScore"),
      Schema("yauza.avro.message.chess.PlayerMove"),
      Schema("yauza.avro.message.chess.GameWithMoveScore"),
      Schema("yauza.avro.message.chess.PlayerGameKpi")
    ),
    Test / avroSchemas := Seq.empty,
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation"
    )
  )

scalafmtOnCompile := true

Global / lintUnusedKeysOnLoad := false
