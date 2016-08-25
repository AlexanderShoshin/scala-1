import CustomAccumulators.WordsCounter
import CustomRDD.{LogRDD, StringRDD}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object BytesCount extends App {
  val conf = new SparkConf().setAppName("BytesCount")
  val sc = new SparkContext(conf)

  val browsersAccumulator = sc.accumulable(mutable.Map[String, Int]().withDefaultValue(0))
  sc.textFile(path = args(0))
      .parseLogs()
      .countBrowsers(browsersAccumulator)
      .countBytes()
      .printSampleAndSave(sampleSize = 5, path = args(1))

  browsersAccumulator.value.foreach{case (browser, usageCount) => println(s"$browser - $usageCount")}
}