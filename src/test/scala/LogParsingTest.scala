import logstat.Log
import org.scalatest.FlatSpec

class LogParsingTest extends FlatSpec {
  val log = "ip3 - - [24/Apr/2011:04:22:45 -0400] \"GET /personal/vanagon_1.jpg HTTP/1.1\" 200 12345 \"http://www.inetgian\" \"Opera/9.80 (X11; Linux i686; U; en) Presto/2.6.30 Version/10.63\""

  it should "extract bytes count from log" in {
    assert(12345 === Log(log).bytes)
  }

  it should "extract id from log" in {
    assert("ip3" === Log(log).id)
  }
}