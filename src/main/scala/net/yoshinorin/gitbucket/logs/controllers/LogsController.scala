package net.yoshinorin.gitbucket.logs.controllers

import scala.util.{Failure, Success, Try}
import gitbucket.core.controller.ControllerBase
import gitbucket.core.util.AdminAuthenticator
import net.yoshinorin.gitbucket.logs.services._
import net.yoshinorin.gitbucket.logs.utils.Error

class LogsController extends ControllerBase with AdminAuthenticator with LogService {

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

    if (Try(lineNum.toInt).toOption != None) {
      val logs = readLog(logBackSettings.confPath, lineNum.toInt) match {
        case Success(s) =>
          s match {
            case Some(s) => Right(s)
            case None => Left(Error.FILE_NOT_FOUND)
          }
        case Failure(f) => Left(Error.FAILURE)
      }
      net.yoshinorin.gitbucket.logs.html.gitbucketlog(defaultDisplayLines, displayLimitLines, logs)
    } else {
      val logs = readLog(logBackSettings.confPath) match {
        case Success(s) =>
          s match {
            case Some(s) => Right(s)
            case None => Left(Error.FILE_NOT_FOUND)
          }
        case Failure(f) => Left(Error.FAILURE)
      }
      net.yoshinorin.gitbucket.logs.html.gitbucketlog(defaultDisplayLines, displayLimitLines, logs)
    }
  })
}
