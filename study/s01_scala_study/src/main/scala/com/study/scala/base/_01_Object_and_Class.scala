package com.study.scala.base

/*

Object 和 Class

1. scala: 完全面向对象，
    故Scala去掉了Java中非面向对象的元素，如static关键字，void类型，
    为了能够调用静态方法，采用伴生对象单例的方式调用方法

    1) static

        scala无static关键字，由object实现类似静态方法的功能（类名.方法名）。
        class关键字和Java中的class关键字作用相同，用来定义一个类；


    2）void

        对于无返回值的函数，Scala定义其返回值类型为Unit类

2. public 修饰符

    scala中没有public关键字，如果不声明权限，就是公共的



 */
object _01_Object_and_Class {

    def main(args: Array[String]): Unit = {

        println("scala print")
        System.out.println("java print")

    }
}
