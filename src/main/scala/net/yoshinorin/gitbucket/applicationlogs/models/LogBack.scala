package net.yoshinorin.gitbucket.applicationlogs.models

import java.io.{File, FileNotFoundException}
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}
import scala.util.{Failure, Success, Try}
import scala.jdk.CollectionConverters._
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil
import ch.qos.logback.core.rolling.RollingFileAppender
import org.slf4j.LoggerFactory
import gitbucket.core.util.StringUtil
import net.yoshinorin.gitbucket.applicationlogs.utils.exceptions

class LogBack private {

  private val ctx = org.slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
  private val configurationFilePath: Option[String] = this.getConfigurationFilePath
  private val isEnable: Boolean = {
    getConfigurationFilePath match {
      case Some(_) => true
      case None => false
    }
  }
  private val logFiles: Option[List[LogFile]] = getLogFiles

  private[this] def getConfigurationFilePath: Option[String] = {
    getRootLoggerContext match {
      case Some(rootLoggerCtx) => {
        val watchList = ConfigurationWatchListUtil.getConfigurationWatchList(rootLoggerCtx).getCopyOfFileWatchList
        Option(watchList.size()) match {
          case None => None
          case Some(_) if watchList.size != 0 => Some(watchList.get(0).toString)
          case Some(_) if watchList.size == 0 => None
        }
      }
      case None => None
    }
  }

  private[this] def getLogFilePaths: Option[List[String]] = {
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

  private[this] def getLogFiles: Option[List[LogFile]] = {
    getLogFilePaths match {
      case Some(paths) => {
        Some(paths.zipWithIndex.map {
          case (v, i) =>
            LogFile(i + 1, v, Charset.defaultCharset()) //TODO: Set CharacterSet from configuration file.
        })
      }
      case None => None
    }
  }

  private def getRootLoggerContext: Option[LoggerContext] = {
    Option(ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).getLoggerContext)
  }

  private def readConfigurationFile: Option[String] = {
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

  private val logger = LoggerFactory.getLogger(getClass)
  private var instance = new LogBack

  def getLogFiles: Option[List[LogFile]] = instance.logFiles

  def isEnable: Boolean = instance.isEnable

  def findById(id: Int): Option[LogFile] = {
    instance.logFiles.get.find(_.id == id)
  }

  def getConfigurationFilePath: Option[String] = instance.configurationFilePath

  def readConfigurationFile: Option[String] = instance.readConfigurationFile

  def reload(): Try[String] = {
    logger.info("Start reload LogBack configuration.")
    instance.getRootLoggerContext match {
      case Some(loggerCtx) => {
        val file = new File(instance.configurationFilePath.get)
        file.exists() match {
          case true => {
            val configurator = new JoranConfigurator
            configurator.setContext(loggerCtx)
            loggerCtx.reset()
            configurator.doConfigure(file)
            instance = new LogBack
            Success("LogBack configuration were reloaded.")
          }
          case false => {
            Failure(new FileNotFoundException("LogBack configuration file not found."))
          }
        }
      }
      case None => {
        Failure(new exceptions.NotFoundException("LogBack Root logger not found."))
      }
    }
  }

}

case class LogFile(
  id: Int,
  path: String,
  characterSet: Charset = Charset.defaultCharset()
)
