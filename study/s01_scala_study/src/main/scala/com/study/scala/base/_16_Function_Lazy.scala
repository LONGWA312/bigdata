package com.study.scala.base

/**
 * 惰性加载
 *      当函数返回值被声明为 lazy 时，函数的执行将被推迟，直到我们首次对此取值，该函数才会执行。这种函数我们称之为惰性函数。
 *
 *
 * 注意：lazy 不能修饰 var 类型的变量
 */
object _16_Function_Lazy {


    def main(args: Array[String]): Unit = {

        lazy val res = sum(10, 20)
        println("------------------------")
        println("------------------------")
        println("------------------------")
        println("------------------------")
        println("------------------------")

        println("res = " + res)

    }

    def sum(a: Int, b: Int): Int ={
        println("执行sum....")
        a + b
    }
}
