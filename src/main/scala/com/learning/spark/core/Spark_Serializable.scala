package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_Serializable
 * @Description TODO
 * @Date 4/20/2020 12:48 PM
 * @Created by Administrator
 */
object Spark_Serializable {
  def main(args: Array[String]): Unit = {

    //1.初始化配置信息及SparkContext
    val sparkConf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    //2.创建一个RDD
    val rdd: RDD[String] = sc.parallelize(Array("hadoop", "spark", "hive", "test"))

    //3.创建一个Search对象
    val search = new Search("h")

    //4.运用第一个过滤函数并打印结果
    val match1: RDD[String] = search.getMatch1(rdd)
    match1.collect().foreach(println)

    val match2: RDD[String] = search.getMatch2(rdd)
    match2.collect().foreach(println)
  }
  //ERROR:object not serializable
  class Search(query:String) extends Serializable{

    //过滤出包含字符串的数据
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    //过滤出包含字符串的RDD
    def getMatch1 (rdd: RDD[String]): RDD[String] = {
      rdd.filter(isMatch)
    }

    //过滤出包含字符串的RDD
    def getMatch2(rdd: RDD[String]): RDD[String] = {
      rdd.filter(x => x.contains(query))
    }

    //此方法無需序列化
    def getMatch3(rdd: RDD[String]): RDD[String] = {
      val q = query//Driver中執行
      rdd.filter(x => x.contains(q))//Executor執行 由於字符串本身可以序列化，無需繼承序列化
    }

  }



}
