package com.study.scala.base

/**
 * 衍生集合

    （1）获取集合的头
    （2）获取集合的尾（不是头的就是尾）
    （3）集合最后一个数据
    （4）集合初始数据（不包含最后一个）
    （5）反转
    （6）取前（后）n 个元素
    （7）去掉前（后）n 个元素
    （8）并集
    （9）交集
    （10）差集
    （11）拉链
    （12）滑窗


 */
object _35_TestList {

    def main(args: Array[String]): Unit = {


        val list1: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
        val list2: List[Int] = List(4, 5, 6, 7, 8, 9, 10)


        /*
            获取集合的头
         */
        print("head: ")
        println(list1.head)

        /*
            获取集合的尾（不是头的就是尾）List(2, 3, 4, 5, 6, 7)
         */
        print("tail: ")
        println(list1.tail)

        /*
            集合最后一个数据
         */
        print("last: ")
        println(list1.last)


        /*
            集合初始化数据（不包含最后一个）
         */
        print("init: ")
        println(list1.init)


        /*
            反转
         */
        print("reverse: ")
        println(list1.reverse)


        /*
            取前、后n个元素
         */
        print("take: ")
        println(list1.take(3))
        println(list1.takeRight(3))


        /*
            去掉前、后n个元素
         */
        print("drop: ")
        println(list1.drop(3))
        println(list1.dropRight(3))


        /*
            并集
         */
        print("union: ")
        println(list1.union(list2))

        /*
            交集
         */
        print("intersect: ")
        println(list1.intersect(list2))



        /*
            差集
         */
        print("diff: ")
        println(list1.diff(list2))


        /*
            拉链 注:如果两个集合的元素个数不相等，那么会将同等数量的数据进行拉链，多余的数据省略不用
         */
        print("zip: ")
        println(list1.zip(list2))

        /*
            滑窗
            sliding(size, step)
            size: 窗口长度
            step: 窗口步长
         */
        println("sliding: ")
        list1.sliding(2, 5).foreach(println)


        list1.sliding(2, 1).foreach(println)

    }
}
