package net.yoshinorin.gitbucket.logs.services

import java.io.File
import java.nio.charset.Charset

import scala.collection.mutable.ArrayBuffer
import org.apache.commons.io.input.ReversedLinesFileReader
import net.yoshinorin.gitbucket.logs.models.Log

import scala.util.Try

trait LogService {

  val defaultDisplayLines: Int = 1000
  val displayLimitLines: Int = 10000

  def readLog(path: String, n: Int = defaultDisplayLines): Try[Option[Log]] = Try {

    val logFile = new File(path)
    if (!logFile.exists()) {
      None
    }

    val r = new ReversedLinesFileReader(new File(path), Charset.defaultCharset())
    try {
      var line = ""
      var lines = ArrayBuffer[String]()
      var counter: Int = 0
      while ((line = r.readLine()) != null && counter < n) {
        lines += line
        counter += 1
      }
      Some(Log(lines.reverse, n))
    } finally {
      if (r != null) r.close()
    }
  }

}
