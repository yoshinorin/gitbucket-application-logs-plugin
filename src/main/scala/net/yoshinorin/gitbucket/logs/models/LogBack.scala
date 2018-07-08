package net.yoshinorin.gitbucket.logs.models

case class LogBackInfo(
  enableLogging: Boolean,
  confPath: String,
  logFilePath: Option[String]
)
