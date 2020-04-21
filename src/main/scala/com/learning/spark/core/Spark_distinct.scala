package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_glom
 * @Description TODO
 * @Date 4/17/2020 9:57 AM
 * @Created by Administrator
 */

object Spark_distinct {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,1,2,6),2)
    //distinct过程中涉及到shuffle，只要一个分区中的数据打乱重组到不同的分区中的数据，则叫shuffle过程。shuffle过程效率低下
    val result: RDD[Int] = source.distinct()
    //由于去重之后数据量可能变少，所以可以在distinct算子中指定partition数
    val result1: RDD[Int] = source.distinct(1)
    result.collect().foreach(println)
    result1.saveAsTextFile("output" )


  }

}
