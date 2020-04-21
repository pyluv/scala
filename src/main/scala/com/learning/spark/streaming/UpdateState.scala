package com.learning.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
 * @Classname UpdateState
 * @Description TODO
 * @Date 4/21/2020 4:46 PM
 * @Created by Administrator
 */
object UpdateState {
  def main(args: Array[String]): Unit = {
    //1.初始化Spark配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamWordCount")

    //2.初始化SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    //保存数据状态，需要设定检查点路径
    ssc.sparkContext.setCheckpointDir("checkpoint")

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

    //使用updateStateByKey来更新状态，统计从运行开始以来单词总的次数
    val stateDStream: DStream[(String, Int)] = wordAndOneStreams.updateStateByKey {
      case (seq, buffer) => {
        val sum = buffer.getOrElse(0) + seq.sum
        Option(sum)
      }
    }

    //将相同的单词次数做统计
    //val wordAndCountStreams = wordAndOneStreams.reduceByKey(_ + _)

    //打印
    stateDStream.print()

    //启动SparkStreamingContext
    ssc.start()
    //Driver等待执行器执行
    ssc.awaitTermination()

  }

}
