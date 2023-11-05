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
    resolvers ++= Seq("confluent".at("https://packages.confluent.io/maven/")),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation"
    ),
    Test / parallelExecution := false
  )

scalafmtOnCompile := true

Global / lintUnusedKeysOnLoad := false
