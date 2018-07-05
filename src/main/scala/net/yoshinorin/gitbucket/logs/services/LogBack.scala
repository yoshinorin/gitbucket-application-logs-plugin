package net.yoshinorin.gitbucket.logs.services

import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
import scala.xml.XML
import gitbucket.core.util.StringUtil
import net.yoshinorin.gitbucket.logs.models.LogBackInfo

object LogBack {

  val notFoundMessage = "Can not find logback configuration file."
  val dosentConfigureMessage = "Dosen't configure Logback."
  val enableLogging = System.getProperties().asScala.toMap.contains("logback.configurationFile")
  val confPath = System.getProperties().asScala.toMap.getOrElse("logback.configurationFile", notFoundMessage)

  val logBackSettingsFile: Either[String, String] = {
    if (enableLogging) {
      try {
        val bytes = Files.readAllBytes(Paths.get(confPath))
        Right(
          StringUtil.convertFromByteArray(bytes)
        )
      } catch {
        case e: Exception => Left("ERROR")
      }
    } else {
      Left(dosentConfigureMessage)
    }
  }

  val logFilePath: Either[String, String] = {
    if (enableLogging) {
      try {
        val xml = logBackSettingsFile match {
          case Left(message) => message
          case Right(s) => {
            (XML.loadString(s) \\ "appender" \ "file" toString).replace("<file>", "").replace("</file>", "")
          }
        }
        if (xml.trim.length == 0) {
          Left("NOT FOUND")
        } else {
          Right(xml)
        }
      } catch {
        case e: Exception => Left("ERROR")
      }
    } else {
      Left(dosentConfigureMessage)
    }
  }

  def getLogBackSettings: LogBackInfo = {
    LogBackInfo(
      System.getProperties().asScala.toMap.contains("logback.configurationFile"),
      System.getProperties().asScala.toMap.getOrElse("logback.configurationFile", notFoundMessage),
      logFilePath
    )
  }
}
