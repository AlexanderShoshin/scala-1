package logstat

import implicits.CustomAccumulators.WordsCounter
import implicits.CustomRDD.{LogRDD, StringRDD}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object BytesCount {
  def execute(dataLocation: DataLocation) = {
    val conf = new SparkConf().setAppName("BytesCount")
    val sc = new SparkContext(conf)
    val browsersAccumulator = sc.accumulable(mutable.Map[String, Int]().withDefaultValue(0))(WordsCounter)

    sc.textFile(path = dataLocation.logsPath)
        .parseLogs()
        .countBrowsers(browsersAccumulator)
        .countBytes()
        .printSampleAndSave(sampleSize = 5, path = dataLocation.outputPath)

    browsersAccumulator.value.foreach{case (browser, usageCount) => println(s"$browser - $usageCount")}
  }
}