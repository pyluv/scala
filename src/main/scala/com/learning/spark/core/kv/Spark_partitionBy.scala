package com.learning.spark.core.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

/**
 * @Classname Spark_partitionBy
 * @Description TODO
 * @Date 4/20/2020 8:38 AM
 * @Created by Administrator
 */
object Spark_partitionBy {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    //partitionBy算子操作的對象一定是(k，v)RDD
    val source: RDD[(Int, String)] = sc.makeRDD(List((1, "a"), (2, "b"), (3, "c"), (4, "d")), 4)
    //根據key的hashcode取余
    val result: RDD[(Int, String)] = source.partitionBy(new HashPartitioner(2))
    println(result.partitions.size)
    val result1: RDD[(Int, String)] = source.partitionBy(new MyPatitioner(3))
    result1.saveAsTextFile("output" )
  }

  //繼承Partitioner類
  class MyPatitioner(partitions: Int) extends Partitioner {
    override def numPartitions: Int = partitions

    //將所有數據放在分區1
    override def getPartition(key: Any): Int = 1
  }
}
