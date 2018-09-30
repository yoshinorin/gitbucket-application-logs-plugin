val Name = "gitbucket-application-logs-plugin"
val Organization = "net.yoshinorin"
val Version = "1.0.0"

lazy val root = project in file(".")

name := Name
organization := Organization
version := Version
scalaVersion := "2.12.7"
gitbucketVersion := "4.23.0"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.6",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalafmtOnCompile := true
coverageExcludedPackages := ".*Plugin.*;.*applicationlogs.html.*"