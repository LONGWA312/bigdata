package com.study.scala.base

/**
 * 枚举类和应用类
 *
 *  1） 说明
        枚举类：需要继承Enumeration
        应用类：需要继承App

 *  Type定义新类型
        使用 type 关键字可以定义新的数据数据类型名称，本质上就是类型的一个别名



 *
 */


/*
    定义枚举类
 */
object Color27 extends Enumeration {
    val RED = Value(1, "red")
    val YELLOW = Value(2, "yellow")
    val BLUE = Value(3, "blue")
}


/*
    定义应用类
 */
object Test27_1 extends App {
    println("xxxxxx")
}

object Test27 {

    def main(args: Array[String]): Unit = {
        /*
            调用枚举类型
         */
        println(Color27.RED)



        /*
            Type定义新类型
         */
        type S = String
        val v: S = "abc"
        def test(): S = "xyz"

    }
}







