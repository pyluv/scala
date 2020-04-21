package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_glom
 * @Description TODO
 * @Date 4/17/2020 9:57 AM
 * @Created by Administrator
 */

object Spark_sortBy {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(List(2,1,3,5))
    //sortBy(func,[ascending], [numTasks])
    //按照自身大小排序
    val result: RDD[Int] = source.sortBy(x => x, true)
    //按照与3余数的大小排序
    val result2: RDD[Int] = source.sortBy(x => x % 3)
    result.collect().foreach(println)
    result2.collect().foreach(println)

  }

}
