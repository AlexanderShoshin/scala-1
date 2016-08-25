import org.apache.spark.AccumulableParam

import scala.collection.mutable

object CustomAccumulators {
  implicit object WordsCounter extends AccumulableParam[mutable.Map[String, Int], String] {
    def addInPlace(keys1: mutable.Map[String, Int],
                   keys2: mutable.Map[String, Int]) = {
      keys2.foreach(p => keys1(p._1) += p._2)
      keys1
    }
    def addAccumulator(counter: mutable.Map[String, Int], key: String) = {
      counter(key) += 1
      counter
    }
    def zero(t: mutable.Map[String, Int]) = {
      mutable.Map[String, Int]().withDefaultValue(0)
    }
  }
}
