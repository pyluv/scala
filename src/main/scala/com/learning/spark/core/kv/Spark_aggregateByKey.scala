package com.learning.spark.core.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_aggregateByKey
 * @Description TODO
 * @Date 4/20/2020 9:46 AM
 * @Created by Administrator
 */
object Spark_aggregateByKey {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[(String, Int)] = sc.parallelize(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
    //取出每个分区相同key对应值的最大值，然后相加
    //zeroValue: U 初始值 分區内
    //seqOp: (U, V) => U 分區内計算邏輯，
    //combOp: (U, U) => U): RDD[(K, U) 分區閒計算邏輯
    val result: RDD[(String, Int)] = source.aggregateByKey(0)(math.max(_, _), _ + _)
    result.collect().foreach(println)

    val value: RDD[(String, Int)] = source.aggregateByKey(0)((x,y)=>{Math.max(x,y)},(x,y)=>{x+y})
    //計算相同key的value之和
    //aggregateByKey的简化操作，seqop和combop相同
    val result1: RDD[(String, Int)] = source.foldByKey(0)(_ + _)
    value.collect().foreach(println)
    result1.collect().foreach(println)
  }

}
