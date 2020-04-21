package com.learning.work

/**
 * @Classname Work3
 * @Description TODO
 * @Date 4/15/2020 5:07 PM
 * @Created by Administrator
 */
object Work3 {
  def main(args: Array[String]): Unit = {

  }

  class Employee () {
    var salary: Int = _
    var name :String = _

    def getAnnual(): Int = {
      salary * 12
    }
  }

  class Worker extends Employee() {
    def work (): Unit = {
      println("working")
    }

    override def getAnnual(): Int = {
      salary * 12
    }
  }

  class Manager extends Employee() {
    var bonus :Int = _
    override def getAnnual(): Int = {
      salary * 12 + bonus
    }
  }

}
