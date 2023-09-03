credentials += Credentials(
  "mymavenrepo.com.read",
  "mymavenrepo.com",
  sys.props("mymavenrepo.username"),
  sys.props("mymavenrepo.password")
)

resolvers ++= Seq("mymavenrepo.com.read" at sys.props("mymavenrepo.url"))

addSbtPlugin("gaia" % "sbt-avrogen" % "1.0")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
