package net.yoshinorin.gitbucket.applicationlogs.models

import scala.collection.mutable.ArrayBuffer

case class DefaultSettings(
  logBackInfo: LogBackInfo,
  defaultDisplayLines: Int,
  displayLimitLines: Int
)

case class Log(
  log: ArrayBuffer[String],
  displayedLines: Int
)
