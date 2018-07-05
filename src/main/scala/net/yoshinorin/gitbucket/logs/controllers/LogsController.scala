package net.yoshinorin.gitbucket.logs.controllers

import scala.util.Try
import gitbucket.core.controller.ControllerBase
import gitbucket.core.util.AdminAuthenticator
import net.yoshinorin.gitbucket.logs.services._

class LogsController extends ControllerBase with AdminAuthenticator {

  val os = net.yoshinorin.gitbucket.logs.services.operatingsystem.OperatingSystem.getInstance

  get("/admin/logs")(adminOnly {
    redirect(s"/admin/logs/logback");
  })

  get("/admin/logs/logback")(adminOnly {
    net.yoshinorin.gitbucket.logs.html.logback(
      LogBack.logBackSettingsFile,
      LogBack.getLogBackSettings
    );
  })

  get("/admin/logs/gitbucketlog")(adminOnly {
    val defaultSettings = GitBucketLog.getDefaultSettings
    val lineNum = request.getParameter("lines")

    if (Try(lineNum.toInt).toOption != None) {
      val n = lineNum.toInt
      if (n > defaultSettings.displayLimitLines) {
        net.yoshinorin.gitbucket.logs.html.gitbucketlog(defaultSettings, os.getLog(defaultSettings.displayLimitLines));
      } else {
        net.yoshinorin.gitbucket.logs.html.gitbucketlog(defaultSettings, os.getLog(n));
      }
    } else {
      net.yoshinorin.gitbucket.logs.html.gitbucketlog(defaultSettings, os.getLog(defaultSettings.defaultDisplayLines));
    }
  })
}
