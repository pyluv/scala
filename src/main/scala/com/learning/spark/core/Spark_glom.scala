package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_glom
 * @Description TODO
 * @Date 4/17/2020 9:57 AM
 * @Created by Administrator
 */
//将一个分区的数据放到一个数组中 返回RDD[Array[T]]
object Spark_glom {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    //与从文件中读取的方法textFile的分区参数不同, textFile的分区参数是最小分区
    val source: RDD[Int] = sc.makeRDD(1 to 18, 4)
    val result: RDD[Array[Int]] = source.glom()
    //取每个分区中最小的数
    val result2: RDD[Int] = result.map(arr => arr.min)
    result.collect().foreach(_.foreach(println))
    result.collect().foreach(arr => {
      println(arr.mkString(","))
    })
    result2.collect().foreach(println)
  }

}
