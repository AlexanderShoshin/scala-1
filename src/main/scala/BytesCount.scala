import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object BytesCount {
  def main(args:Array[String]) : Unit = {
    val conf = new SparkConf().setAppName("BytesCount")
    val sc = new SparkContext(conf)
    val tf = sc.textFile(args(0))
    val bytesById = tf.map((line: String) => {
      val log = ServerLog(line)
      (log.id, (log.bytes, 1))
    })
    .filter {case (_, (bytes, _)) => bytes > 0}
    .reduceByKey {case ((sumBytes, sumCount), (bytes, count)) => (sumBytes + bytes, sumCount + count)}
    .mapValues {case (sumBytes, count) => (sumBytes/count, sumBytes)}
    .sortBy({case (_, (_, sumBytes)) => sumBytes}, ascending = false)

    submitResult(bytesById, args(1))
  }

  def submitResult(rdd: RDD[(String, (Int, Int))], path: String) = {
    rdd.cache()
    rdd.take(5).foreach(map => println(">>> id = " + map._1 + ", bytes = " + map._2))
    rdd.saveAsTextFile(path)
  }
}