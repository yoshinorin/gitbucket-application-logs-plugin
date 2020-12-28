import gitbucket.core.controller.Context
import gitbucket.core.plugin._
import io.github.gitbucket.solidbase.model.Version
import net.yoshinorin.gitbucket.applicationlogs.controllers.ApplicationLogsController

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "application-logs"
  override val pluginName: String = "Application Logs Plugin"
  override val description: String = "Log feature support for Administrator."
  override val versions: List[Version] = List(
    new Version("0.1.0"),
    new Version("0.2.0"),
    new Version("1.0.0"),
    new Version("2.0.0"),
    new Version("3.0.0")
  )

  override val systemSettingMenus: Seq[(Context) => Option[Link]] = Seq(
    (context: Context) => Some(Link("application-logs", "Application Logs", "admin/application-logs", Some("file-text")))
  )

  override val controllers: Seq[(String, ApplicationLogsController)] = Seq(
    "/*" -> new ApplicationLogsController
  )
}
