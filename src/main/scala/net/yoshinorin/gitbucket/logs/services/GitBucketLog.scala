package net.yoshinorin.gitbucket.logs.services

import net.yoshinorin.gitbucket.logs.models.{DefaultSettings, Log}

object GitBucketLog {

  def getDefaultSettings: DefaultSettings = {
    DefaultSettings(
      LogBack.getLogBackSettings,
      1000,
      30000
    )
  }

}
