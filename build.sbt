val Name = "gitbucket-application-logs-plugin"
val Organization = "net.yoshinorin"
val Version = "3.1.0"

lazy val root = project in file(".")

name := Name
organization := Organization
version := Version
scalaVersion := "2.13.8"
gitbucketVersion := "4.37.2"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.6",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

scalafmtOnCompile := true
coverageExcludedPackages := ".*Plugin.*;.*applicationlogs.html.*"
