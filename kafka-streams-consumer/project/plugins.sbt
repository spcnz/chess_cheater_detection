credentials += Credentials(
  "mymavenrepo.com.read",
  "mymavenrepo.com",
  sys.props("mymavenrepo.username"),
  sys.props("mymavenrepo.password")
)

resolvers ++= Seq("mymavenrepo.com.read" at sys.props("mymavenrepo.url"))

addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.16")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
addSbtPlugin("gaia" % "sbt-avrogen" % "1.0.0")
  