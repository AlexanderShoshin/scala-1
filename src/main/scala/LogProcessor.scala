import org.apache.spark.rdd.RDD

object LogProcessor {
  implicit class LogRDD(log: RDD[String]) {
    def submit(path: String) = {
      log.cache()
      log.take(5).foreach(value => println(value))
      log.saveAsTextFile(path)
    }
    def countBytes() =
      log.map((line: String) => {
        val log = Log(line)
        (log.id, (log.bytes, 1))
      })
      .filter {case (_, (bytes, _)) => bytes > 0}
      .reduceByKey {case ((sumBytes, sumCount), (bytes, count)) => (sumBytes + bytes, sumCount + count)}
      .mapValues {case (sumBytes, count) => (sumBytes/count, sumBytes)}
      .sortBy({case (_, (_, sumBytes)) => sumBytes}, ascending = false)
      .map({case (id, (avgBytes, sumBytes)) => s"$id,$avgBytes,$sumBytes"})
  }
}