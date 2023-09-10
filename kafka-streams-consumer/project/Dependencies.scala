import sbt._

object Dependencies {
  val streamsCommonVersion = "2.0.0"
  // format: off
  val all: Seq[ModuleID] = Seq(
    "gaia" %% "skyline-kafka-streams-common" % streamsCommonVersion,
    "gaia" %% "skyline-kafka-streams-common-test" % streamsCommonVersion % Test,
  )
}
