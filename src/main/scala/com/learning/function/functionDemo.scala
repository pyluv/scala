package com.learning.function

/**
 * @Classname functionDemo
 * @Description TODO
 * @Date 4/15/2020 10:26 AM
 * @Created by Administrator
 */
object functionDemo {
  def main(args: Array[String]): Unit = {
    val n1 = 21
    val n2 = 20
   // println(getResult(n1, n2, '6'))


   // println(peach(1))

    //println(printTower(10))
    println(print99())
  }

  def getResult(n1: Int, n2: Int, oper: Char) = {
    if (oper == '+') {
      n1 + n2
    } else if (oper == '-') {
      n1 - n2
    } else null
  }
  /*
   猴子吃桃子问题:有一堆桃子，猴子第一天吃了其中的一半，并再多吃了一个！以后每天猴子都吃其中的一半，然后再多吃一个。当到第十天时，想再吃时（还没吃），发现只有1个桃子了。问题：最初共多少个桃子
    分析思路
    1. day = 10 桃子有 1
    2. day = 9  桃子有  (day10[1] + 1) *2
    3. day = 8  桃子有  (day9[4] + 1) * 2

     */
  def peach(day: Int): Int = {
    if (day == 10) {
      1
    } else {
      (peach(day + 1) + 1) * 2
    }
  }

  def printTower(n:Int)  {
    for (i<- 1 to n) {
      println("*" * i)
    }
  }

  def print99(): Unit ={

    for (i<- 1 to 9; j<- 1 to 9) {
      println(i + "*" + j + "=" + i*j)
    }
  }

}
