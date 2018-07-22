package net.yoshinorin.gitbucket.applicationlogs.controllers

import scala.util.{Failure, Success, Try}
import org.slf4j.LoggerFactory
import gitbucket.core.controller.ControllerBase
import gitbucket.core.util.AdminAuthenticator
import net.yoshinorin.gitbucket.applicationlogs.services._
import net.yoshinorin.gitbucket.applicationlogs.utils.Error

class ApplicationLogsController extends ControllerBase with AdminAuthenticator with ApplicationLogService with LogBackService {

  private val logger = LoggerFactory.getLogger(getClass)
  private val logBackSettings = getLogBackSettings

  get("/admin/application-logs")(adminOnly {
    redirect(s"/admin/application-logs/logback")
  })

  get("/admin/application-logs/logback")(adminOnly {
    net.yoshinorin.gitbucket.applicationlogs.html.logback(
      isEnable,
      getLogBackConfigurationFilePath,
      readLogBackConfigurationFile,
      logBackSettings
    )
  })

  get("/admin/application-logs/gitbucketlog")(adminOnly {

    val lineNum = request.getParameter("lines")

    logBackSettings.logFilePath match {
      case Some(path) => {
        var n = defaultDisplayLines
        if (Try(lineNum.toInt).toOption != None) {
          n = lineNum.toInt
        }
        val logs = readLog(path, n) match {
          case Success(s) =>
            s match {
              case Some(s) => Right(s)
              case None => Left(Error.FILE_NOT_FOUND)
            }
          case Failure(f) => {
            logger.error(f.toString)
            Left(Error.FAILURE)
          }
        }
        net.yoshinorin.gitbucket.applicationlogs.html.gitbucketlog(defaultDisplayLines, displayLimitLines, logs, n)
      }
      case _ => {
        Left(Error.FAILURE)
      }
    }
  })
}
