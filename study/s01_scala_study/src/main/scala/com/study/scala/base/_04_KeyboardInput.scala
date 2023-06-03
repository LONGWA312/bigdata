package com.study.scala.base

import scala.io.StdIn


/*
键盘输入
    要接收用户输入的数据，就可以使用键盘输入语句来获取。

1. 基本语法
    StdIn.readLine()
    StdIn.readShort()
    StdIn.readDouble()
    。。。


 */
object _04_KeyboardInput {


    def main(args: Array[String]): Unit = {

        println("input name：")
        val name = StdIn.readLine()

        println("input age:")
        val age = StdIn.readShort()

        println("input sal:")
        val sal = StdIn.readDouble()

        println("name = " + name)
        println("age = " + age)
        println("sal = " + sal)

    }

}
