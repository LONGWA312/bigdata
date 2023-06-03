package com.study.scala.base

/**

 * 集合常用函数 -- 基本属性和常用操作

    （1）获取集合长度
    （2）获取集合大小
    （3）循环遍历
    （4）迭代器
    （5）生成字符串
    （6）是否包含

 */


object _34_Collection {


    def main(args: Array[String]): Unit = {

        val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
        /*
            获取集合长度
         */
        println(list.length)

        /*
            获取集合大小
         */
        println(list.size)

        /*
            循环遍历
         */
        list.foreach(println)

        /*
            迭代器
         */
        for (elem <- list.iterator) {
            println(elem)
        }

        /*
            生成字符串
         */
        println(list.mkString(","))

        /*
            是否包含
         */
        println(list.contains(3))

    }

}
