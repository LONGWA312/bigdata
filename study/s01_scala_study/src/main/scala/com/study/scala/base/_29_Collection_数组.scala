package com.study.scala.base

import scala.collection.mutable.ArrayBuffer


/**
 **
数组
 **
 不可变数组
    *1）第一种方式定义数组
        *定义：val arr1 = new Array[Int](10)
        *（1）new 是关键字
        *（2）[Int]是指定可以存放的数据类型，如果希望存放任意数据类型，则指定 Any
        *（3）(10)，表示数组的大小，确定后就不可以变化
 *
 *
 */
object TestArray{

    def main(args: Array[String]): Unit = {

        /*
            定义数组
         */
        val arr01 = new Array[Int](4)
        println(arr01.length)


        /*
            数组赋值
            修改某个元素的值
         */
        arr01(3) = 10

        /*
            采用方法的形式给数组赋值
         */
        arr01.update(0, 1)


        /*
            遍历数组
            查看数组
         */
        println(arr01.mkString(","))


        /*
            普通遍历
         */
        for (i <- arr01) {
            println(i)
        }


        /*
            简化遍历
         */
        def printx(elem: Int): Unit = {
            printx(elem)
        }

        arr01.foreach(printx)
        arr01.foreach((x) => {printx(x)})
        arr01.foreach(printx(_))
        arr01.foreach(println)


        /*
            增加元素，由于创建的是不可变数组，增加元素，其实是产生新的数组）
         */
        println(arr01)

        val ints: Array[Int] = arr01 :+ 5
        println(ints)

    }
}


/**

     第二种方式定义数组
        val arr1 = Array(1, 2)
        （1）在定义数组时，直接赋初始值
        （2）使用 apply 方法创建数组对象
 */

object ArrayTest2{


    def main(args: Array[String]): Unit = {

        val arr01 = Array(1, 3, "bobo")
        println(arr01.length)
        for (i <- arr01) {
            println(i)
        }

    }
}


/**

 可变数组

    1.定义变长数组
        val arr01 = ArrayBuffer[Any](3, 5, 2)
        （1）[Any]存放任意数据类型
        （2）(3, 2, 5)初始化好的三个元素
        （3）ArrayBuffer 需要引入 scala.collection.mutable.ArrayBuffer

    2.注意
        （1）ArrayBuffer 是有序的集合
        （2）增加元素使用的是 append 方法()，支持可变参数
 */

import scala.collection.mutable.ArrayBuffer
object TestArrayBuffer{


    def main(args: Array[String]): Unit = {

        /*
            创建并初始赋值可变数组
         */
        val arr01 = ArrayBuffer[Any](1, 2, 3)

        for (i <- arr01) {
            println(i)
        }
        println(arr01.length)
        println("arr01.hash=" + arr01.hashCode())


        /*
            增加元素
         */
        arr01.+=(4)

        /*
            向数组最后追加元素
         */
        arr01.append(5, 6)

        /*
            向指定的位置插入元素
         */
        arr01.insert(0, 7, 8)
        println("arr01.hash=" + arr01.hashCode())


        /*
            修改元素
         */
        arr01(1) = 9

        for (elem <- arr01) {
            println(elem)
        }

        println(arr01.length)

    }

}


/**
不可变数组与可变数组的转换
    1）说明
        arr1.toBuffer //不可变数组转可变数组
        arr2.toArray //可变数组转不可变数组

        （1）arr2.toArray 返回结果才是一个不可变数组，arr2 本身没有变化
        （2）arr1.toBuffer 返回结果才是一个可变数组，arr1 本身没有变化
 */

object TestArrayBuffer2{


    def main(args: Array[String]): Unit = {

        /*
            创建一个空的可变数组
         */
        val arr01 = ArrayBuffer[Int]()


        arr01.append(1, 2, 3)
        println(arr01)


        /*
            可变数组 转 不可变数组
            arr01.toArray 返回的结果是一个新的定长数组集合
            arr01 它没有变化
         */
        val newArr = arr01.toArray
        println(newArr)
        println(arr01)


        /*
            Array ===> ArrayBuffer
            newArr.toBuffer 返回一个变长数组 newArr2
            newArr 没有任何变化，依然是定长数组
         */
        val bufferArr = newArr.toBuffer
        bufferArr.append(123)

        println(bufferArr)

    }

}


/**

 多维数组

    多维数组定义
        val arr = Array.ofDim[Double](3,4)

    说明：
        二维数组中有三个一维数组，每个一维数组中有四个元素

 */
object DimArray{

    def main(args: Array[String]): Unit = {

        /*
            创建了一个二维数组, 有三个元素，每个元素是，含有 4 个元素一维数组()
         */
        val arr = Array.ofDim[Int](3, 4)
        arr(1)(2)


        for (i <- arr) {
            for (j <- i) {
                println(j + " ")
            }
            println()
        }

    }
}


