package net.yoshinorin.gitbucket.applicationlogs.services

import java.io.File
import java.nio.charset.Charset
import scala.util.Try
import org.apache.commons.io.input.ReversedLinesFileReader
import net.yoshinorin.gitbucket.applicationlogs.models.LogFile
import net.yoshinorin.gitbucket.applicationlogs.utils.SortTypes

trait ApplicationLogService {

  val defaultDisplayLines: Int = 1000
  val displayLimitLines: Int = 10000
  val sortTypes: List[SortTypes] = List(SortTypes.ASC, SortTypes.DESC)

  def readLog(logfile: LogFile, n: Int = defaultDisplayLines): Try[Option[List[String]]] = Try {
    val logFile = new File(logfile.path)
    if (!logFile.exists()) {
      None
    }

    val reversedReader = new ReversedLinesFileReader(new File(logfile.path), Charset.defaultCharset())
    try {
      val counter = Iterator.from(0)
      Some(Iterator.continually(reversedReader.readLine()).takeWhile(_ != null && { counter.next < n }).toList.reverse)
    } finally {
      if (reversedReader != null) reversedReader.close()
    }
  }

}
