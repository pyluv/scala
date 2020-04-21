package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_groupBy
 * @Description TODO
 * @Date 4/17/2020 10:15 AM
 * @Created by Administrator
 */
object Spark_groupBy {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(1 to 18)
    //按照指定规则进行分组
    //分组后的数据形成了对偶元组（k，v），k表示分组的key，v表示分组数据的集合
    val result: RDD[(Int, Iterable[Int])] = source.groupBy(data => data % 3)
    //根据余数是否等于2进行过滤
    val result2: RDD[Int] = source.filter(data => data % 3 == 2)
    result.collect().foreach(println)
    result2.collect().foreach(println)

  }

}
