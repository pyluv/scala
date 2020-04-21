package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname WordCount
 * @Description TODO
 * @Date 4/16/2020 10:29 AM
 * @Created by Administrator
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    //设定spark计算框架的部署环境
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    //读取文件
    //从hdfs中读取文件
    val lines: RDD[String] = sc.textFile("hdfs://hadoop102:9000/hello.txt")
    //val lines: RDD[String] = sc.textFile("input", 2)
    //将一行一行的分解成一个个的单词
    val words: RDD[String] = lines.flatMap((x => x.split(" ")))
    //将数据结构转换为（_, 1)
    val map: RDD[(String, Int)] = words.map((x => (x, 1)))
    //聚合
    val result: RDD[(String, Int)] = map.reduceByKey((x,y) => x+y)
    //将结果采集后打印
    val tuples = result.collect()
    tuples.foreach(println)
    result.saveAsTextFile("output")


  }

}
