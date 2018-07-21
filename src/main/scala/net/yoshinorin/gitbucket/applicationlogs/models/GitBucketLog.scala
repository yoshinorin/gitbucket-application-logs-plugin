package net.yoshinorin.gitbucket.applicationlogs.models

case class DefaultSettings(
  logBackInfo: LogBackInfo,
  defaultDisplayLines: Int,
  displayLimitLines: Int
)
