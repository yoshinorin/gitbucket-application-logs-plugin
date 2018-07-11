package net.yoshinorin.gitbucket.logs.services

import java.io._
import java.nio.charset.Charset
import scala.collection.mutable.ArrayBuffer
import org.apache.commons.io.input.ReversedLinesFileReader
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

  def getLog(num: Int = GitBucketLog.getDefaultSettings.defaultDisplayLines): Either[String, Log] = {
    if (GitBucketLog.getDefaultSettings.logBackInfo.enableLogging) {
      GitBucketLog.getDefaultSettings.logBackInfo.logFilePath match {
        case None => Left("NOT FOUND")
        case Some(p) => {
          try {
            val logFile = new File(p)
            if (!logFile.exists()) {
              Left("FILE NOT EXISTS")
            }

            val r = new ReversedLinesFileReader(new File(p), Charset.defaultCharset())
            try {
              var line = ""
              var lines = ArrayBuffer[String]()
              var counter: Int = 0
              while ((line = r.readLine()) != null && counter < num) {
                lines += line
                counter += 1
              }
              Right(
                Log(
                  lines.reverse,
                  num
                )
              )
            } finally {
              if (r != null) r.close()
            }
          }
        }
      }
    } else {
      Left(GitBucketLog.disableMessage)
    }
  }

}
