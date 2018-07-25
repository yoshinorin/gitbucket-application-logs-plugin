package net.yoshinorin.gitbucket.applicationlogs.models

import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil
import ch.qos.logback.core.rolling.RollingFileAppender
import org.slf4j.LoggerFactory
import gitbucket.core.util.StringUtil

object LogBack {

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

  def readLogBackConfigurationFile: Option[String] = {
    getLogBackConfigurationFilePath match {
      case Some(s) => {
        val bytes = Files.readAllBytes(Paths.get(s))
        Some(StringUtil.convertFromByteArray(bytes))
      }
      case None => None
    }
  }

  def getLogFilePaths: Option[List[String]] = {

    var paths = List[String]()
    for (logger <- ctx.getLoggerList.asScala) {
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
    if (paths.size != 0) {
      Some(paths)
    } else {
      None
    }
  }

}
