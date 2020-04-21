package com.learning.spark.core.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * @Classname Spark_reduceByKy
 * @Description TODO
 * @Date 4/20/2020 9:44 AM
 * @Created by Administrator
 */
object Spark_reduceByKey {
//  1. reduceByKey：按照key进行聚合，在shuffle之前有combine（预聚合）操作，返回结果是RDD[k,v]。
//  2. groupByKey：按照key进行分组，直接进行shuffle。
//  3. 开发指导：reduceByKey比groupByKey，建议使用。但是需要注意是否会影响业务逻辑

  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[String] = sc.makeRDD(Array("aaa", "bbb", "aaa", "ccc"),4)
    val mapRDD: RDD[(String, Int)] = source.map((x => (x, 1)))
    // mapRDD.reduceByKey(_ + _) =>  mapRDD.reduceByKey((x,y) => x + y)
    val result: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    result.collect().foreach(println)

  }

}
