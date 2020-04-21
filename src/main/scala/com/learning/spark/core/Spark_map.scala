package com.learning.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Classname Spark_map
 * @Description TODO
 * @Date 4/17/2020 8:50 AM
 * @Created by Administrator
 */
object Spark_map {

  //所有算子中的计算功能都是由Executor执行
  //其余代码在Driver中执行
  //这就引出一个文题，即：在Driver中的变量，若在计算中使用，由于需要网络IO的传输，则必须可以序列化
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文对象
    val sc = new SparkContext(conf)
    val source: RDD[Int] = sc.makeRDD(1 to 10, 2)
    //RDD 的map算子返回的是RDD
    val result: RDD[Int] = source.map(x => x * 2)
    //mapPartitions可以对一个RDD中所有的分区进行遍历，有几个分区执行几遍方法体
    //mapPartitions的效率优于map算子，减少了发送到Executor的交互次数
    //mapPartitions可能会出现内存溢出（OOM）例如：当一个分区的数据量 > Executor的内存的时候
    val result2: RDD[Int] = source.mapPartitions(datas => {
      //mapPartitions中处理的是集合，所以下面的map是scala中的scala方法
      datas.map(data => data * 2)
    })
    //mapPartitionsWithIndex中有两个参数：分区号 和 分区集合
    val result3: RDD[(Int, String)] = source.mapPartitionsWithIndex {
      case (index, datas) => {
        datas.map((_, "partition index = " + index))
      }
    }
    val result4: RDD[(Int, String)] = source.mapPartitionsWithIndex((index, datas) => {
      datas.map((_, "partition index = " + index))
    })
    //RDD中collect方法返回Array[T]，便于输出
    result.collect().foreach(println)
    result2.collect().foreach(println)
    result3.collect().foreach(println)
    result4.collect().foreach(println)

  }


}
