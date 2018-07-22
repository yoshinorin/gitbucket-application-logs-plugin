package net.yoshinorin.gitbucket.applicationlogs.services

import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
import scala.xml.XML
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil
import ch.qos.logback.core.rolling.RollingFileAppender
import org.slf4j.LoggerFactory
import gitbucket.core.util.StringUtil
import net.yoshinorin.gitbucket.applicationlogs.models.LogBackInfo
import net.yoshinorin.gitbucket.applicationlogs.utils.Error

trait LogBackService {

  val enableLogging = System.getProperties().asScala.toMap.contains("logback.configurationFile")
  val confPath = System.getProperties().asScala.toMap.getOrElse("logback.configurationFile", Error.NOT_FOUND_LOGBACK_SETTINGS.message)

  private val ctx = org.slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

  def isEnable: Boolean = {
    getLogBackConfigurationFilePath match {
      case Some(v) => true
      case _ => false
    }
  }

  def getLogBackConfigurationFilePath: Option[String] = {
    val rootLoggerCtx = ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).getLoggerContext
    val watchList = ConfigurationWatchListUtil.getConfigurationWatchList(rootLoggerCtx).getCopyOfFileWatchList()
    watchList.size() match {
      case 0 => None
      case _ => Some(watchList.get(0).toString)
    }
  }

  def getLogFilePaths: Option[List[String]] = {
    import scala.collection.JavaConversions._

    var paths = List[String]()
    for (logger <- ctx.getLoggerList) {
      val i = logger.iteratorForAppenders
      while ({
        i.hasNext
      }) {
        val appender = i.next
        if (appender.isInstanceOf[FileAppender[_]] || appender.isInstanceOf[RollingFileAppender[_]]) {
          paths :+= appender.asInstanceOf[FileAppender[ILoggingEvent]].getFile
        }
      }
    }
    if (paths.size() != 0) {
      Some(paths)
    } else {
      None
    }
  }

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
