package com.learning.map

import scala.collection.mutable

/**
 * @Classname MapDemo
 * @Description TODO
 * @Date 4/15/2020 7:30 PM
 * @Created by Administrator
 */
object MapDemo {
  def main(args: Array[String]): Unit = {
    var map = mutable.Map("a"-> 10, "b" -> "", "c" ->30)
    val res = map("a")
    val list1 = List(1,2,3);
    var list2 = List[Int]()

    for (item <- list1) {
      list2 = list2 :+ item*2
    }

    map("a9") = "90"
    map = map - ("a")
    for ((k,v) <- map) println(k + v)
    for (k <- map.keys) println(k )
    for (v <- map.values) println(v )
    for (a <- map) println(a._1, a._2)

  }



}
