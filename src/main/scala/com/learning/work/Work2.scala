package com.learning.work

/**
 * @Classname Work2
 * @Description TODO
 * @Date 4/15/2020 11:48 AM
 * @Created by Administrator
 */
object Work2 {
  def main(args: Array[String]): Unit = {
    //1、一个数字如果为正数，则它的signum为1;如果是负数,则signum为-1;如果为0,则signum为0.编写一个函数来计算这个值
    val n = new Num
    var res: Long = 1
    n.num = -2
    validateNumber(n)
    println(n.signum)

    //5、编写一个for循环,计算字符串中所有字母的Unicode代码（toLong方法）的乘积。举例来说，"Hello"中所有字符串的乘积为9415087488L

//    for (i<- "Hello") {
//      res= res * i.toLong
//    }


    "Hello".foreach(res *= _.toLong)

    println(res)


    def validateNumber (n:Num): Unit = {
      if (n.num > 0) {
        n.signum = 1
      }else n.signum = -1
    }

  }
  class Num {
    var signum: Int =_
    var num:Int=_
  }

}
