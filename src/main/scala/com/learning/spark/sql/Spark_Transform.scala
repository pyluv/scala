package com.learning.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * @Classname Spark_Transform
 * @Description TODO
 * @Date 4/21/2020 10:02 AM
 * @Created by Administrator
 */
object Spark_Transform {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark_sql")
    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    //创建RDD
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "zhangsan", 20), (2, "test", 30), (3, "pyluv", 40)))
    //rdd转换DF
    //导入隐式转换
    //这里的spark不是包名。是SparkSession对象的名字
    import spark.implicits._

    val df: DataFrame = rdd.toDF("id", "name", "age")
    //DF to DS
    val ds: Dataset[User] = df.as[User]

    //DS TO DF
    val df1: DataFrame = ds.toDF()

    //DF TO RDD
    val rdd1: RDD[Row] = df1.rdd
    //获取数据时，通过索引(列)来访问数据
    rdd1.foreach(row => println(row.getInt(0)))

    //RDD TO DS
    val userRDD: RDD[User] = rdd.map {
      case (id, name, age) => {
        User(id,name,age)
      }
    }

    //DS TO RDD
    val userDS: Dataset[User] = userRDD.toDS()
    val rdd2: RDD[User] = userDS.rdd
    rdd2.foreach(println)

    spark.stop()
  }
}
case class User(id: Int, name: String, age: Int)
