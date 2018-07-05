package net.yoshinorin.gitbucket.logs.services.operatingsystem

import net.yoshinorin.gitbucket.logs.models._
import net.yoshinorin.gitbucket.logs.services._

class Other extends GitBucketLog {

  override def getLog(lines: Int = GitBucketLog.getDefaultSettings.defaultDisplayLines): Either[String, Log] = {
    Left("NOT SUPPORTED")
  }
}
