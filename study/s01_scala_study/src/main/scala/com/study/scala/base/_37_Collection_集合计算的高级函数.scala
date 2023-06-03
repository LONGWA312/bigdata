package com.study.scala.base

/**
 * 集合计算的高级函数

    1）说明
        （1）过滤
            遍历一个集合并从中获取满足指定条件的元素组合一个新的集合

        （2）转化/映射（map）
            将集合中的每一个元素映射到某一个函数

        （3）扁平化

        （4）扁平化 + 映射
            flatMap 相当于先进行 map 操作，在进行 flatten 操作
            集合中的每个元素的子元素映射到某个函数并返回新集合

        （5）分组（group）
            按照指定的规则对集合的元素进行分组

        （6）简化（归约）

        （7）折叠

 */
object _37_TestList {


    def main(args: Array[String]): Unit = {

        val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val nestedList: List[List[Int]] = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
        val wordList: List[String] = List("hello world", "hello scala", "hello spark")


        /*
            过滤
         */
        println(list.filter(x => x % 2 == 0 ))


        /*
            转化、映射
         */
        println(list.map(x => x + 1))


        /*
            扁平化
         */
        println(nestedList.flatten)


        /*
            flatMap 相当于先进行 map 操作，在进行 flatten 操作
         */
        println(wordList.flatMap(x => x.split(" ")))


        /*
           分组
         */
        println(list.groupBy(x => x % 2))

    }
}


/**
 * Reduce方法
    Reduce 简化（归约） ：通过指定的逻辑将集合中的数据进行聚合，从而减少数据，最终获取结果。
 */
object TestReduce{


    def main(args: Array[String]): Unit = {

        val list = List(1, 2, 3, 4)

        /*
            将数据两两结合，实现运算规则
         */
        val i: Int = list.reduce((x, y) => x - y)
        println("i = " + i)


        /*
            从源码角度讲，reduce底层调用的其实就是reduceLeft
            ((1-2)-3)-4=-8
         */
        val i1: Int = list.reduceLeft((x, y) => x - y)
        println(i1)

        /*
            ((4-3)-2)-1=-2
         */
        val i2 = list.reduceRight((x, y) => x - y)
        println(i2)
    }
}



/**
    Fold方法
    Fold折叠： 简化的一种特殊情况
 */
object TestFold{

    def main(args: Array[String]): Unit = {
        main()
        main2()
    }

    def main(): Unit = {

        val list = List(1, 2, 3, 4)

        /*
            fold 方法使用了函数柯里化，存在两个参数列表
            第一个参数列表为 ： 零值（初始值）
            第二个参数列表为： 简化规则
            fold 底层其实为 foldLeft
         */
        val i = list.foldLeft(1)((x,y) => x-y)
        val i1 = list.foldRight(10)((x,y) => x-y)

        println(i)
        println(i1)

    }


    import scala.collection.mutable
    def main2(): Unit ={
        // 两个 Map 的数据合并
        val map1 = mutable.Map("a"->1, "b"->2, "c"->3)
        val map2 = mutable.Map("a"->4, "b"->5, "d"->6)
        val map3: mutable.Map[String, Int] = map2.foldLeft(map1)
        {
            (map, kv) => {
                val k = kv._1
                val v = kv._2
                map(k) = map.getOrElse(k, 0) + v
                map
            }
        }
        println(map3)

    }




}
