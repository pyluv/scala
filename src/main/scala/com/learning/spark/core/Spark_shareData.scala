package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_shareData
 * @Description TODO
 * @Date 4/20/2020 6:31 PM
 * @Created by Administrator
 */
object Spark_shareData {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val result: Int = source.reduce(_ + _)
    println(result)
    //输出sum仍然为0，这是因为driver与executor的数据无法共享造成的
    var sum :Int  =0
    val result1: Unit = source.foreach(x => sum + x)
    println(result1)
    //使用累加器来共享变量
    val accumulator: LongAccumulator = sc.longAccumulator
    source.foreach{
      case i => {
        accumulator.add(i)
      }
    }
    println(accumulator.value)
    sc.stop()
  }

}
