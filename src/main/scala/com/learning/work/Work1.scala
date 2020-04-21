package com.learning.work

/**
 * @Classname Work1
 * @Description TODO
 * @Date 4/15/2020 8:55 AM
 * @Created by Administrator
 */
object Work1 {

  def main(args: Array[String]): Unit = {
    //计算3的平方根,然后再对该值求平方。现在，这个结果与3相差多少？
    val sRoot = math.sqrt(3)
    val square = sRoot * sRoot
    println(3-square)

    //8、Scala允许你用数字去乘一个字符串，去REPL中试一下"crazy"*3。这个操作做什么？在Scaladoc中如何找到这个操作?
    println ("crazy" * 3)
    //9、10 max 2的含义是什么？max方法定义在哪个类中 RichInt
    println(10 max 2)
    //10、用BigInt计算2的1024次方
    var num1: Int = 10
    var bigInt : BigInt = 2
    var result : BigInt = bigInt.pow(1024)
    println(result)
    println(BigInt(2).pow(1024))
    //11、在Scala中如何获取字符串“Hello”的首字符和尾字符？
    //首字符
    println("hello".charAt(0))
    println("hello".take(1))
    println("hello"(0))
    //尾字符
    println("hello".charAt(4))
    println("hello".reverse.take(1))
    println("hello".takeRight(1))
  }

}
