import org.apache.spark.{SparkConf, SparkContext}
import LogProcessor.LogRDD

object BytesCount extends App {
  val conf = new SparkConf().setAppName("BytesCount")
  val sc = new SparkContext(conf)
  sc.textFile(path = args(0))
  .countBytes()
  .submit(path = args(1))
}