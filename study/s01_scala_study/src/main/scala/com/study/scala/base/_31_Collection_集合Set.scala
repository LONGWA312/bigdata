package com.study.scala.base

/**

 Set 集合
    默认情况下，Scala 使用的是不可变集合，如果你想使用可变集合，需要引用scala.collection.mutable.Set 包

 不可变 Set
    1）说明
        （1）Set 默认是不可变集合，数据无序
        （2）数据不可重复
        （3）遍历集合
 */
object TestSet{


    def main(args: Array[String]): Unit = {

        /*
            Set 默认是不可变集合，数据无序
         */
        val set = Set(1, 2 ,3, 4, 5, 6)
        println(set.mkString(","))


        /*
            数据不可重复
         */
        val set1 = Set(1, 2 ,3, 4, 5, 6, 5, 4, 3)
        println(set1.mkString(","))


        /*
            集合遍历
         */
        for (elem <- set1) {
            println(elem)
        }


        set1.foreach(println)

    }
}


/**
可变 mutable.Set
    1）说明
        （1）创建可变集合 mutable.Set
        （2）打印集合
        （3）集合添加元素
        （4）向集合中添加元素，返回一个新的 Set
        （5）删除数
 */

import scala.collection.mutable
object TestMutableSet{

    def main(args: Array[String]): Unit = {

        /*
            创建可变集合
         */
        val set = mutable.Set(1, 2, 3, 4, 5, 6)


        /*
            集合添加元素
         */
        set += 8


        /*
            相机和中添加元素返回一个新的set
         */
        val ints = set.+(9)
        println("ints=" + ints)
        println("set=" + set)


        /*
            删除数据
         */
        println(set.mkString(","))
        set.-=(5)
        println(set.mkString(","))
        set.foreach(println)





    }

}
