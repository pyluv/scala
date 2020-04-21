package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_glom
 * @Description TODO
 * @Date 4/17/2020 9:57 AM
 * @Created by Administrator
 */

object Spark_coalesce {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(1 to 16,4)
    //缩减分区前的分区数
    println(source.partitions.size)
    source.saveAsTextFile("output1")
    val result: RDD[Int] = source.coalesce(3)
    //缩减分区后的分区数
    println(result.partitions.size)
    //保存到文件中可以看出,原RDD的第3，4分区合并成了缩减分区后的3分区。
    //缩减分区即可以理解成合并分区
    result.saveAsTextFile("output2")
  }

}
