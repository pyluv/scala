package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_glom
 * @Description TODO
 * @Date 4/17/2020 9:57 AM
 * @Created by Administrator
 */

object Spark_repartition {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(1 to 16,4)
    //缩减分区前的分区数
    println(source.partitions.size)
    source.saveAsTextFile("output1")
    //coalesce重新分区可以选择是否进行shuffle，默认不进行
    //repartition实际上是调用了coalesce，默认使用shuffle
    val result: RDD[Int] = source.repartition(2)
    //缩减分区后的分区数
    println(result.partitions.size)
    //缩减分区即可以理解成合并分区
    result.saveAsTextFile("output2")
  }

}
