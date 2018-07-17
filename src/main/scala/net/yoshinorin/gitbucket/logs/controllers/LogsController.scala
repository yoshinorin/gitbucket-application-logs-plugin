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

    //TODO: Fix this code
    logBackSettings.logFilePath match {
      case Some(path) => {
        if (Try(lineNum.toInt).toOption != None) {
          val logs = readLog(path, lineNum.toInt) match {
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
        } else {
          val logs = readLog(path) match {
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
      }
      case _ => {
        Left(Error.FAILURE)
      }
    }
  })
}
