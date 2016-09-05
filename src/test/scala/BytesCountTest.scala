import implicits.CustomAccumulators.WordsCounter
import implicits.CustomRDD.LogRDD
import com.holdenkarau.spark.testing.SharedSparkContext
import logstat.Log
import org.scalatest.FlatSpec

import scala.collection.mutable

class BytesCountTest extends FlatSpec with SharedSparkContext {
  it should "count words with custom accumulator" in {
    val wordsLog = List(
      "key",
      "key",
      "value",
      "key")
    val expectedVal = mutable.Map(
      "key" -> 3,
      "value" -> 1)
    assert(expectedVal === countWords(wordsLog))
  }

  def countWords(log: List[String]) = {
    val wordsAccumulator = sc.accumulable(mutable.Map[String, Int]().withDefaultValue(0))
    sc.parallelize(log, 2).map(wordsAccumulator += _).collect()
    wordsAccumulator.value
  }

  it should "count average and total bytes by ip" in {
    val bytesLog = List(
      "ip1 100",
      "ip1 200",
      "ip2 1",
      "ip2 1")
    val expectedVal = List(
      "ip1,150,300",
      "ip2,1,2")
    assert(expectedVal === countBytes(bytesLog))
  }

  def countBytes(log: List[String]) =
    sc.parallelize(log)
        .map(line => Log(line.split(" ")(0),
                         line.split(" ")(1).toInt,
                         ""))
        .countBytes()
        .collect()
        .toList
}