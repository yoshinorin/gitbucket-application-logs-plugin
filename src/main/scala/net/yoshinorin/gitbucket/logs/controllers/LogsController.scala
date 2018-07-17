package net.yoshinorin.gitbucket.logs.controllers

import scala.util.{Failure, Success, Try}
import org.slf4j.LoggerFactory
import gitbucket.core.controller.ControllerBase
import gitbucket.core.util.AdminAuthenticator
import net.yoshinorin.gitbucket.logs.services._
import net.yoshinorin.gitbucket.logs.utils.Error

class LogsController extends ControllerBase with AdminAuthenticator with LogService {

  private val logger = LoggerFactory.getLogger(getClass)

  get("/admin/logs")(adminOnly {
    redirect(s"/admin/logs/logback")
  })

  get("/admin/logs/logback")(adminOnly {
    net.yoshinorin.gitbucket.logs.html.logback(
      LogBack.logBackSettingsFile,
      LogBack.getLogBackSettings
    )
  })

  get("/admin/logs/gitbucketlog")(adminOnly {
    val logBackSettings = LogBack.getLogBackSettings
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
        net.yoshinorin.gitbucket.logs.html.gitbucketlog(defaultDisplayLines, displayLimitLines, logs)
      }
      case _ => {
        Left(Error.FAILURE)
      }
    }
  })
}
