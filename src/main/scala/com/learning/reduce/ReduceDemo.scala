package com.learning.reduce

/**
 * @Classname ReduceDemo
 * @Description TODO
 * @Date 4/15/2020 8:49 PM
 * @Created by Administrator
 */
object ReduceDemo {
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4,5, -9999)
    def compare (num1:Int, num2:Int): Int = {
      if (num1 < num2) {
        num1
      } else num2
    }
    println(list.reduce(compare))
  }

}
