package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark01_RDD
 * @Description TODO
 * @Date 4/16/2020 5:12 PM
 * @Created by Administrator
 */
object Spark_RDD {

  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    //从Spark上下文创建RDD
    val listRDD: RDD[Double] = sc.makeRDD(List(1, 2.3, 4))
    //从内存中创建RDD
    var arrayRDD: RDD[Int] = sc.parallelize(Array(1, 2, 3))
    //从外部存储中创建
    val textRDD: RDD[String] = sc.textFile("in")
    //将得到内容写到文件中
    //listRDD.saveAsTextFile("output")
    //使用自定义分区
//    val listRDD2: RDD[Double] = sc.makeRDD(List(1, 2.3, 4), 2)
//    listRDD2.saveAsTextFile("output2")
    val testRDD2: RDD[String] = sc.textFile("input1", 2)
    testRDD2.saveAsTextFile("output")
  }
}
