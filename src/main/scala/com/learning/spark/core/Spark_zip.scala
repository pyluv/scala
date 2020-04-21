package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_glom
 * @Description TODO
 * @Date 4/17/2020 9:57 AM
 * @Created by Administrator
 */

object Spark_zip {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    //zip要求非常严格，只有当两个集合的分区数且分区中的元素数量一致，才可以用zip算子。zip不需要shuffle
    val source: RDD[Int] = sc.makeRDD(1 to 4,4)
    val source1: RDD[String] = sc.makeRDD(List("a", "b", "c", "d"),4)
    val result: RDD[(Int, String)] = source.zip(source1)
    result.collect().foreach(println)
  }

}
