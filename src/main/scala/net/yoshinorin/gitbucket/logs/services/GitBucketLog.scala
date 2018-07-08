package net.yoshinorin.gitbucket.logs.services

import java.io.IOException
import scala.sys.process._
import net.yoshinorin.gitbucket.logs.models.{DefaultSettings, Log}

object GitBucketLog {
  val disableMessage = "Log setting is disable."

  def getDefaultSettings: DefaultSettings = {
    DefaultSettings(
      LogBack.getLogBackSettings,
      1000,
      30000
    )
  }
}

trait GitBucketLog {
  def getLog(lines: Int = GitBucketLog.getDefaultSettings.defaultDisplayLines): Either[String, Log] = {
    if (GitBucketLog.getDefaultSettings.logBackInfo.enableLogging) {
      GitBucketLog.getDefaultSettings.logBackInfo.logFilePath match {
        case None => Left("NOT FOUND")
        case Some(p) => {
          try {
            Right(
              Log(
                Process(s"tail -n $lines $p").!!,
                lines
              ))
          } catch {
            case e: IOException => Left("ERROR")
          }
        }
      }
    } else {
      Left(GitBucketLog.disableMessage)
    }
  }
}
