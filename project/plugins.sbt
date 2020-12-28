logLevel := Level.Warn

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.3.15")
addSbtPlugin("io.github.gitbucket" % "sbt-gitbucket-plugin" % "1.5.0")
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1")
// TODO: https://github.com/pureconfig/pureconfig/pull/799
addSbtPlugin("net.ruippeixotog" % "sbt-coveralls" % "1.3.0")