package net.yoshinorin.gitbucket.applicationlogs.services

import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
import scala.xml.XML
import gitbucket.core.util.StringUtil
import net.yoshinorin.gitbucket.applicationlogs.models.LogBackInfo
import net.yoshinorin.gitbucket.applicationlogs.utils.Error

object LogBack {

  val enableLogging = System.getProperties().asScala.toMap.contains("logback.configurationFile")
  val confPath = System.getProperties().asScala.toMap.getOrElse("logback.configurationFile", Error.NOT_FOUND_LOGBACK_SETTINGS.message)

  val logBackSettingsFile: Option[String] = {
    if (enableLogging) {
      val bytes = Files.readAllBytes(Paths.get(confPath))
      Some(StringUtil.convertFromByteArray(bytes))
    } else {
      None
    }
  }

  val logFilePath: Option[String] = {
    logBackSettingsFile match {
      case Some(s) => Some((XML.loadString(s) \\ "appender" \ "file" toString).replace("<file>", "").replace("</file>", ""))
      case None => None
    }
  }

  def getLogBackSettings: LogBackInfo = {
    LogBackInfo(
      System.getProperties().asScala.toMap.contains("logback.configurationFile"),
      System.getProperties().asScala.toMap.getOrElse("logback.configurationFile", Error.NOT_FOUND_LOGBACK_SETTINGS.message),
      logFilePath
    )
  }
}
