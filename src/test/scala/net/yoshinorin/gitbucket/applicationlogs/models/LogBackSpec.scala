import org.scalatest._
import net.yoshinorin.gitbucket.applicationlogs.models.LogBack

import scala.util.Success

class LogBackSpec extends FlatSpec with DiagrammedAssertions {

  val configFilePath = getClass.getClassLoader.getResource("logback-test.xml").getPath

  "LogBack object" should "return isEnable true" in {
    assert(LogBack.isEnable)
  }

  "LogBack object" should "get LogFiles" in {
    val logfilesWithOption = LogBack.getLogFiles
    for {
      logfiles <- logfilesWithOption
    } {
      assert(logfiles.size == 2)
      assert(logfiles(0).path == "/etc/logs/gitbucket.log")
      assert(logfiles(1).path == "/etc/logs/gitbucket-2.log")
    }
  }

  "LogBack object" should "find LogBack case class by id" in {
    val logback = LogBack.findById(1).get
    assert(logback.path == "/etc/logs/gitbucket.log")
  }

  "LogBack object" should "read configulation file" in {
    val configration = LogBack.readConfigurationFile.get
    assert(configration.startsWith("<!--First line-->"))
    assert(configration.endsWith("<!--Last line-->"))
  }

  "LogBack object" should "can reload" in {
    assert(LogBack.reload() == Success("LogBack configuration were reloaded."))
  }
}
