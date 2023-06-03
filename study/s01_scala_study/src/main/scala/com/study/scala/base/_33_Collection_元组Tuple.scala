package com.study.scala.base

/**
 *
 *  元组 （Tuple）

        元组也是可以理解为一个容器，可以存放各种相同或不同类型的数据。说的简单点，就是将多个无关的数据封装为一个整体，称为元组。

    注意：
        元组中最大只能有 22 个元素。

    实操：
        （1）声明元组的方式：(元素 1，元素 2，元素 3)
        （2）访问元组
        （3）Map 中的键值对其实就是元组,只不过元组的元素个数为 2，称之为对偶
 *
 */
object TestTuple{


    def main(args: Array[String]): Unit = {

        /*
            （1）声明元组的方式：(元素 1，元素 2，元素 3)
         */
        val tuple: (Int, String, Boolean) = (40, "bobo", true)

        /*
            （2）访问元组
            （2.1）通过元素的顺序进行访问，调用方式：_顺序号
         */
        println(tuple._1)
        println(tuple._2)
        println(tuple._3)


        /*
            （2.2）通过索引访问元素
         */
        println(tuple.productElement(0))


        /*
            (2.3)遍历访问元组
         */
        for (elem <- tuple.productIterator) {
            println(elem)
        }


        /*
            Map 中的键值对其实就是元组,只不过元组的元素个数为 2，称之为对偶
         */
        val map1: Map[String, Int] = Map("a" -> 1, "b" -> 2, "c" -> 3)
        val map2: Map[String, Int] = Map(("a", 1), ("b", 2), ("c", 3))

        map2.foreach((tuple) => println(tuple._1 +" -> " + tuple._2))


    }

}
