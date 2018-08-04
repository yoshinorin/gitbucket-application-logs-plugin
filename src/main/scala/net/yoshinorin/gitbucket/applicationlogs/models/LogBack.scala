package net.yoshinorin.gitbucket.applicationlogs.models

import java.nio.charset.Charset
import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil
import ch.qos.logback.core.rolling.RollingFileAppender
import gitbucket.core.util.StringUtil

class LogBack {

  private[this] val ctx = org.slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
  private val configurationFilePath: Option[String] = this.getConfigurationFilePath
  private val isEnable: Boolean = {
    getConfigurationFilePath match {
      case Some(v) => true
      case _ => false
    }
  }

  private[this] def getConfigurationFilePath: Option[String] = {
    val rootLoggerCtx = ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).getLoggerContext
    val watchList = ConfigurationWatchListUtil.getConfigurationWatchList(rootLoggerCtx).getCopyOfFileWatchList
    watchList.size() match {
      case 0 => None
      case _ => Some(watchList.get(0).toString)
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
    if (paths.nonEmpty) {
      Some(paths)
    } else {
      None
    }
  }

  def readConfigurationFile: Option[String] = {
    getConfigurationFilePath match {
      case Some(s) => {
        val bytes = Files.readAllBytes(Paths.get(s))
        Some(StringUtil.convertFromByteArray(bytes))
      }
      case None => None
    }
  }

}

object LogBack {

  private var instance = new LogBack

  var logFiles: Option[List[LogFile]] = getLogFiles

  private def getLogFiles: Option[List[LogFile]] = {
    instance.getLogFilePaths match {
      case Some(paths) => {
        Some(paths.zipWithIndex.map {
          case (v, i) =>
            LogFile(i + 1, v, Charset.defaultCharset()) //TODO: Set CharacterSet from configuration file.
        })
      }
      case None => None
    }
  }

  def isEnable: Boolean = instance.isEnable

  def findById(id: Int): Option[LogFile] = {
    logFiles.get.find(_.id == id)
  }

  def getConfigurationFilePath: Option[String] = instance.configurationFilePath

  def readConfigurationFile: Option[String] = instance.readConfigurationFile

  def reload(): Unit = {
    instance = new LogBack
  }

}

case class LogFile(
  id: Int,
  path: String,
  characterSet: Charset = Charset.defaultCharset()
)
