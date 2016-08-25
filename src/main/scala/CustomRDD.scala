import org.apache.spark.Accumulable
import org.apache.spark.rdd.RDD

import scala.collection.mutable

object CustomRDD {
  implicit class StringRDD(stringRDD: RDD[String]) {
    def parseLogs() = {
      stringRDD
          .map(Log(_))
          .filter(_.bytes > 0)
    }

    def printSampleAndSubmit(sampleSize: Int, path: String) = {
      stringRDD.cache()
      stringRDD.take(sampleSize).foreach(value => println(value))
      stringRDD.saveAsTextFile(path)
    }
  }

  implicit class LogRDD(logRDD: RDD[Log]) {
    def countBrowsers(counter: Accumulable[mutable.Map[String, Int], String]) = {
      logRDD.map(log => {
        counter += log.browser
        log
      })
    }

    def countBytes() =
      logRDD
          .map(log => (log.id, (log.bytes, 1)))
          .reduceByKey{case ((sumBytes, sumCount), (bytes, count)) => (sumBytes + bytes, sumCount + count)}
          .mapValues{case (sumBytes, count) => (sumBytes/count, sumBytes)}
          .sortBy({case (_, (_, sumBytes)) => sumBytes}, ascending = false)
          .map({case (id, (avgBytes, sumBytes)) => s"$id,$avgBytes,$sumBytes"})
  }
}