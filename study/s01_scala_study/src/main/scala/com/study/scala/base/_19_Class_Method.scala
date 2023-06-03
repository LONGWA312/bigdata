package com.study.scala.base

/**
 *  类方法
    1）基本语法
        def 方法名(参数列表) [：返回值类型] = {
            方法体
        }
 */

class Person3{

    def sum(a: Int, b: Int): Int = {
        a + b
    }
}


object Person3{

    def main(args: Array[String]): Unit = {

        val person = new Person3()
        println(person.sum(10, 20))

    }
}
