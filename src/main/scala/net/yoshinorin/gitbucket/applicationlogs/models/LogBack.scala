package net.yoshinorin.gitbucket.applicationlogs.models

case class LogBackInfo(
  enableLogging: Boolean,
  confPath: String,
  logFilePath: Option[String]
)
