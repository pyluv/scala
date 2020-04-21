package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_flatMap
 * @Description TODO
 * @Date 4/17/2020 9:48 AM
 * @Created by Administrator
 */
object Spark_flatMap {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[List[Int]] = sc.makeRDD(Array(List(1, 2), List(3, 4)))
    val result: RDD[Int] = source.flatMap(datas => datas)
    result.collect().foreach(println)
  }

}
