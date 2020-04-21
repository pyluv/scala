package com.learning.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Classname KafkaWordCount
 * @Description TODO
 * @Date 4/21/2020 4:16 PM
 * @Created by Administrator
 */
object KafkaWordCount {
  //bin/kafka-topics.sh --zookeeper hadoop102:2181 --list
  //bin/kafka-topics.sh --zookeeper hadoop102:2181 --create --topic spark --partitions 2 --replication factor 2
  //bin/kafka-console-producer.sh --broker-list hadoop102:9092 --topic spark

  def main(args: Array[String]): Unit = {
    //1.初始化Spark配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamWordCount")

    //2.初始化SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    //从Kafka中采集数据
    val kafkaDStream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(
      ssc,
      "hadoop102:2181",
      "spark",
      Map("spark" -> 3)
    )

    //将每一行数据做切分，形成一个个单词
    val wordStreams = kafkaDStream.flatMap(t=>t._2.split(" "))

    //将单词映射成元组（word,1）
    val wordAndOneStreams = wordStreams.map((_, 1))

    //将相同的单词次数做统计
    val wordAndCountStreams = wordAndOneStreams.reduceByKey(_ + _)

    //打印
    wordAndCountStreams.print()

    //启动SparkStreamingContext
    ssc.start()
    //Driver等待执行器执行
    ssc.awaitTermination()
  }

}
