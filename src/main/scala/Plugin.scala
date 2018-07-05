import gitbucket.core.controller.Context
import gitbucket.core.plugin._
import net.yoshinorin.gitbucket.logs.controllers.LogsController

import io.github.gitbucket.solidbase.model.Version

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "logs"
  override val pluginName: String = "Logs Plugin"
  override val description: String = "Display LogBack's Settings and GitBucket's log."
  override val versions: List[Version] = List(
    new Version("0.1.0")
  )

  override val systemSettingMenus: Seq[(Context) => Option[Link]] = Seq(
    (context: Context) => Some(Link("logs", "Application Logs", "admin/logs", Some("file-text")))
  )

  override val controllers = Seq(
    "/*" -> new LogsController
  )
}
