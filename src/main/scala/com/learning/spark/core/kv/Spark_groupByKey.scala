package com.learning.spark.core.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_groupByKey
 * @Description TODO
 * @Date 4/20/2020 8:58 AM
 * @Created by Administrator
 */
object Spark_groupByKey {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[String] = sc.makeRDD(Array("aaa", "bbb", "aaa", "ccc"),4)
    val mapRDD: RDD[(String, Int)] = source.map((x => (x, 1)))
    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()
    groupRDD.saveAsTextFile("output")
    //通過groupByKey完成word count
    val result: RDD[(String, Int)] = groupRDD.map {
      case (c, datas) => {
        (c, datas.sum)
      }
    }
    result.collect().foreach(println)
  }
}
