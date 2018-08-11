import org.scalatest._
import net.yoshinorin.gitbucket.applicationlogs.utils.{SortType, Error}

class TypesSpec extends FlatSpec with DiagrammedAssertions {

  "SortType ASC" should "get value" in {
    assert(SortType.ASC.value == "asc")
  }

  "SortType DESC" should "get value" in {
    assert(SortType.DESC.value == "desc")
  }

  "Error FAILURE" should "get message" in {
    assert(Error.FAILURE.message == "ERROR")
  }

  "Error NOT_FOUND_LOGBACK_SETTINGS" should "get message" in {
    assert(Error.NOT_FOUND_LOGBACK_SETTINGS.message == "Can not find logback configuration file")
  }

  "Error FILE_NOT_FOUND" should "get message" in {
    assert(Error.FILE_NOT_FOUND.message == "FILE NOT FOUND")
  }

}
