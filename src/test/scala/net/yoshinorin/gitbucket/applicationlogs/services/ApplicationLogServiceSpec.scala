package net.yoshinorin.gitbucket.applicationlogs.services

import org.scalatest._
import net.yoshinorin.gitbucket.applicationlogs.models.LogFile

class ApplicationLogServiceSpec extends FlatSpec with DiagrammedAssertions with ApplicationLogService {

  val logfilePath = getClass.getClassLoader.getResource("test.log").getPath

  "readLog" should "return all line" in {
    val linesWithTry = readLog(LogFile(1, logfilePath))
    for {
      linesWithSome <- linesWithTry
      lines <- linesWithSome
    } {
      assert(lines.size == 4)
      assert(lines.head == "first line")
      assert(lines.last == "last line")
    }
  }

  "readLog" should "return two line" in {
    val linesWithTry = readLog(LogFile(1, logfilePath), 2)
    for {
      linesWithSome <- linesWithTry
      lines <- linesWithSome
    } {
      assert(lines.size == 2)
      assert(lines.head == "third line")
      assert(lines.last == "last line")
    }
  }

  "readLog" should "return None" in {
    val linesWithTry = readLog(LogFile(1, ""))
    for {
      linesWithSome <- linesWithTry
      lines <- linesWithSome
    } {
      assert(lines == None)
    }
  }

}
