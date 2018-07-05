package net.yoshinorin.gitbucket.logs.models

case class DefaultSettings(
  logBackInfo: LogBackInfo,
  defaultDisplayLines: Int,
  displayLimitLines: Int
)

case class Log(
  log: String,
  displayedLines: Int
)
