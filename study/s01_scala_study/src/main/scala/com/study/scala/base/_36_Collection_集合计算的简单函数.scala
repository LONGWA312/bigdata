package com.study.scala.base

/**
 * 集合计算简单函数

    1）说明
        （1）求和
        （2）求乘积
        （3）最大值
        （4）最小值
        （5）排序



    2）特别注意
        （1）sorted
            对一个集合进行自然排序，通过传递隐式的Ordering
        （2）sortBy
            对一个属性或多个属性进行排序，通过他的类型
        （3）sortWith
            基于函数的排序，通过comparator函数，实现自定义排序的逻辑


 */
object _36_TestList{

    def main(args: Array[String]): Unit = {

        val list: List[Int]  = List(1, 5, -3, 4, 2, -7, 6)

        /*
            求和
         */
        println(list.sum)


        /*
            求乘积
         */
        println(list.product)


        /*
            取最大值
         */
        println(list.max)

        /*
            取最小值
         */
        println(list.min)


        /*
            按元素大小排序
         */
        println(list.sortBy(x => x))

        /*
            按元素绝对值大小排序
         */
        println(list.sortBy(x => x.abs))


        /*
            按元素大小升序排序
         */
        println(list.sortWith((x, y) => x < y))


        /*
            按元素大小降序排序
         */
        println(list.sortWith((x, y) => x > y))

    }
}
