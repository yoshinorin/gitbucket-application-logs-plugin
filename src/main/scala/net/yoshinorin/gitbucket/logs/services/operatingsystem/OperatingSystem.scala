package net.yoshinorin.gitbucket.logs.services.operatingsystem

import java.io.IOException
import scala.sys.process._

object OperatingSystem {
  sealed abstract class OSType
  case object Linux extends OSType
  case object Windows extends OSType
  case object Mac extends OSType
  case object Other extends OSType

  val osArch = System.getProperty("os.arch")
  val osName = System.getProperty("os.name")

  val osType: OSType =
    if (osName.toLowerCase.contains("linux") || osName.toLowerCase.contains("mac")) Linux
    else if (osName.toLowerCase.contains("windows")) Windows
    else Other

  val distribution: String = osType match {
    case Linux => {
      try {
        Process("cat /etc/issue").!!.replace("\\n", "").replace("\\l", "").replace(" ", "")
      } catch {
        case e: IOException => "ERROR"
      }
    }
    case _ => {
      "-"
    }
  }

  val getInstance = osType match {
    case OperatingSystem.Linux => new Linux
    case OperatingSystem.Windows => new Windows
    case _ => new Other
  }
}
