package com.learning.spark.sql


import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row, SparkSession, TypedColumn}

/**
 * @Classname Spark_UDAF
 * @Description TODO
 * @Date 4/21/2020 10:30 AM
 * @Created by Administrator
 */
object Spark_UDAF_Class {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark_sql")
    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._

    //创建聚合函数
    val udaf = new MyAVG1
    //将聚合函数转换为查询的列
    val avgCol: TypedColumn[UserBean, Double] = udaf.toColumn.name("avgAge")

    val frame: DataFrame = spark.read.json("input/name.json")

    val userDS: Dataset[UserBean] = frame.as[UserBean]

    //应用函数
    userDS.select(avgCol).show()
    spark.stop()
  }
}

case class UserBean (name:String, age:Long)
case class AvgBuffer(var sum:Long, var count: Int)

//声明用户自定义聚合函数（强类型）
//继承org.apache.spark.sql.expressions.Aggregator
class MyAVG1 extends Aggregator[UserBean, AvgBuffer, Double] {
  //初始化
  override def zero: AvgBuffer = {
    AvgBuffer(0, 0)
  }

  //聚合数据
  override def reduce(b: AvgBuffer, a: UserBean): AvgBuffer = {
    b.sum += a.age
    b.count += 1
    b
  }

  //缓冲区的合并操作
  override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    b1
  }

  //完成计算
  override def finish(reduction: AvgBuffer): Double = {
    reduction.sum.toDouble / reduction.count
  }

  //自定义类型用Encoders.product
  override def bufferEncoder: Encoder[AvgBuffer] = Encoders.product

  //基本数据类型用Encoders.scalaXXX
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
