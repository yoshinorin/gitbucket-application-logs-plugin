package net.yoshinorin.gitbucket.logs.services.operatingsystem

import java.io.IOException
import scala.sys.process._
import net.yoshinorin.gitbucket.logs.models._
import net.yoshinorin.gitbucket.logs.services._

class Windows extends GitBucketLog {

  override def getLog(lines: Int = GitBucketLog.getDefaultSettings.defaultDisplayLines): Either[String, Log] = {
    if (GitBucketLog.getDefaultSettings.logBackInfo.enableLogging) {
      GitBucketLog.getDefaultSettings.logBackInfo.logFilePath match {
        case Left(message) => Left("NOT FOUND")
        case Right(p) => {
          try {
            Right(
              Log(
                Process(s"powershell -Command Get-Content -Path $p -Tail $lines").!!,
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
