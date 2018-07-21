val Name = "gitbucket-application-logs-plugin"
val Organization = "net.yoshinorin"
val Version = "0.1.0"

lazy val root = project in file(".")

name := Name
organization := Organization
version := Version
scalaVersion := "2.12.6"
gitbucketVersion := "4.23.0"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.6"
)

scalafmtOnCompile := true