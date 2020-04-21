package com.learning.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
 * @Classname Spark_UDAF
 * @Description TODO
 * @Date 4/21/2020 10:30 AM
 * @Created by Administrator
 */
object Spark_UDAF {
  def main(args: Array[String]): Unit = {
    //创建SparkConf对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark_sql")
    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._

    //创建聚合函数
    val udaf = new MyAVG
    //注册聚合函数
    spark.udf.register("avgAge", udaf)
    val frame: DataFrame = spark.read.json("input/name.json")
    frame.createOrReplaceTempView("user")
    spark.sql("select avgAge(age) from user").show()
    spark.stop()
  }
}

//继承UserDefinedAggregateFunction
class MyAVG extends UserDefinedAggregateFunction {
  //函数输入的数据结构
  override def inputSchema: StructType = {
    new StructType().add("age", LongType)
  }

  //计算时的数据结构
  override def bufferSchema: StructType = {
    new StructType().add("sum", LongType).add("count", LongType)
  }

  //函数返回的数据类型
  override def dataType: DataType = DoubleType

  //函数是否稳定
  override def deterministic: Boolean = true

  //计算前缓冲区的初始化（sum = 0，count = 0)
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L//0代表sum
    buffer(1) = 0L//1代表count
  }

  //根据查询结构更新缓冲区数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1
  }

  //将多个节点的缓冲区合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //sum
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    //count
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  //计算
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1)
  }
}
