package com.learning.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}


/**
 * @Classname Spark_sql
 * @Description TODO
 * @Date 4/21/2020 9:37 AM
 * @Created by Administrator
 */
object Spark_sql {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark_sql")

    //构造器私有
    //val sparkSession:SparkSession  = new SparkSession(sparkConf)
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    val frame: DataFrame = spark.read.json("input/name.json")
    //将DataFrame转换成一张表
    frame.createOrReplaceTempView("user")
    spark.sql("select * from user").show()
    //frame.show()
    spark.stop()
  }

}
