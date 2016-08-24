import LogProcessor.LogRDD
import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.FlatSpec

class BytesCountTest extends FlatSpec with SharedSparkContext {
  it should "count average and total bytes by ip" in {
    val rawLog = List(
      "ip1 - - [1/Jan/1970:00:00:00 -0000] \"request\" 200 100 \"host\" \"browser\"",
      "ip1 - - [1/Jan/1970:00:00:00 -0000] \"request\" 200 200 \"host\" \"browser\"",
      "ip2 - - [1/Jan/1970:00:00:00 -0000] \"request\" 200 1 \"host\" \"browser\"",
      "ip2 - - [1/Jan/1970:00:00:00 -0000] \"request\" 200 1 \"host\" \"browser\"")
    val result = List(
      "ip1,150,300",
      "ip2,1,2")
    assert(result === processLog(rawLog))
  }

  it should "ignore lines without bytes value" in {
    val rawLog = List(
      "ip1 - - [1/Jan/1970:00:00:00 -0000] \"request\" 200 100 \"host\" \"browser\"",
      "ip1 - - [1/Jan/1970:00:00:00 -0000] \"request\" 304 - \"host\" \"browser\"")
    val result = List("ip1,100,100")
    assert(result === processLog(rawLog))
  }

  def processLog(log: List[String]) =
    sc.parallelize(log).countBytes().collect().toList
}