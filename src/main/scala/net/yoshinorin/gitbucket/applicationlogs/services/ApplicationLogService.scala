package net.yoshinorin.gitbucket.applicationlogs.services

import java.io.File
import java.nio.charset.Charset
import scala.collection.mutable.ArrayBuffer
import scala.util.Try
import org.apache.commons.io.input.ReversedLinesFileReader

trait ApplicationLogService {

  val defaultDisplayLines: Int = 1000
  val displayLimitLines: Int = 10000

  def readLog(path: String, n: Int = defaultDisplayLines): Try[Option[ArrayBuffer[String]]] = Try {

    val logFile = new File(path)
    if (!logFile.exists()) {
      None
    }

    val r = new ReversedLinesFileReader(new File(path), Charset.defaultCharset())
    try {
      var counter: Int = 0
      var line: String = ""
      var lines = ArrayBuffer[String]()
      while ({ line = r.readLine(); line != null } && counter <= n) {
        lines += line
        counter += 1
      }
      Some(lines.reverse)
    } finally {
      if (r != null) r.close()
    }
  }

}
