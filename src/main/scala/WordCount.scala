/**
 * @Classname WordCount
 * @Description TODO
 * @Date 4/16/2020 9:58 AM
 * @Created by Administrator
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    val lines = List("aaa aaa ccc lu", "aaa", "scala")

    val res1 = lines.flatMap((s: String) => s.split(" "))
    println(res1)
    val res2 = res1.map((s: String) => (s, 1))
    println(res2)
    val res3 = res2.groupBy((x: (String, Int)) => x._1)
    println(res3)
    val res4 = res3.map((x: (String, List[(String, Int)])) => (x._1, x._2.size))
    println(res4)
    val res5 = res4.toList.sortBy((x: (String, Int)) => x._2).reverse
    println(res5)

    println(lines.flatMap(_.split(" ")).map((_, 1)).groupBy((_._1)).map((x => (x._1, x._2.size))).toList.sortBy(_._2).reverse)
  }

}
