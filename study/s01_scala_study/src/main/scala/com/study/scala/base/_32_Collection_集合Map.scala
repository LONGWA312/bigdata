package com.study.scala.base

/**
 *
 Map 集合
    Scala 中的 Map 和 Java 类似，也是一个散列表，它存储的内容也是键值对（key-value）映射


 不可变 Map
    1）说明
        （1）创建不可变集合 Map
        （2）循环打印
        （3）访问数据
        （4）如果 key 不存在，返回 0
 */
object TestMap{


    def main(args: Array[String]): Unit = {

        /*
            创建不可变集合Map
         */
        val map = Map("a" -> 1, "b" -> 2, "c" -> 3)


        /*
            访问数据
         */
        for (elem <- map.keys) {
            /*
                使用 get 访问 map 集合的数据，会返回特殊类型 Option(选项):
                    有值（Some）
                    无值(None)
             */
            println(elem + " = " + map.get(elem))

        }



        /*
            如果key不存在，返回0
         */
        println(map.get("d").getOrElse(0))
        println(map.getOrElse("d", 0))


        /*
            循环打印
         */
        map.foreach((kv) => println(kv))

    }
}


/**
可变 Map
    1）说明
        （1）创建可变集合
        （2）打印集合
        （3）向集合增加数据
        （4）删除数据
        （5）修改数据
 */

import scala.collection.mutable
object TestMutableMap{


    def main(args: Array[String]): Unit = {

        /*
            创建可变集合
         */
        val map = mutable.Map("a" -> 1, "b" -> 2, "c" -> 3)

        /*
            向集合中添加数据
         */
        map.+=("d" -> 4)

        /*
            将数组4添加到集合，并把集合中原值1返回
         */
        val maybeInt: Option[Int] = map.put("a", 4)
        println(maybeInt.getOrElse(0))

        /*
            删除数据
         */
        map.-=("b", "c")

        /*
            修改数据
         */
        map.update("d", 5)
        map("d") = 5


        /*
            打印集合
         */
        map.foreach((kv) => println(kv))


    }

}