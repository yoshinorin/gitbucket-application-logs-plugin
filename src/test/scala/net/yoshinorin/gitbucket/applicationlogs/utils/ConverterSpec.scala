import org.scalatest._
import net.yoshinorin.gitbucket.applicationlogs.utils.SortType
import net.yoshinorin.gitbucket.applicationlogs.utils.Converter.stringConverter

class ConverterSpec extends FlatSpec with DiagrammedAssertions {

  "toSortType" should "return ASC object" in {
    assert("asc".toSortType == SortType.ASC)
  }

  "toSortType" should "return DESC object" in {
    assert("desc".toSortType == SortType.DESC)
  }

  "toSortType" should "return default object" in {
    assert("test".toSortType == SortType.ASC)
  }

}
