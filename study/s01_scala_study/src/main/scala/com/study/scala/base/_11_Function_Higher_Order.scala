package com.study.scala.base

/**
 函数高级

    高阶函数
        对于一个函数我们可以：定义函数、调用函数，但是其实函数还有更高阶的用法

        1）函数可以作为值进行传递

        2）函数可以作为参数进行传递

        3）函数可以作为函数返回值返回

 */
object _11_Function_Higher_Order {

    def main(args: Array[String]): Unit = {
        main1()
    }


    /**
     * 1）函数可以作为值进行传递
     */
    def main1(): Unit = {



        /*
            定义喊出 foo()
         */
        def foo(): Int = {
            println("foo ....")
            1
        }


        /*
            1）函数可以作为值进行传递
         */
        val f = foo()
        println(f)

        val f1 = foo
        println(f1)



        /*
            （2）在被调用函数 foo 后面加上 _，相当于把函数 foo 当成一个整体，传递给变量 f2
         */
        val f2 = foo _

        foo()
        f2()


        /*
            （3）如果明确变量类型，那么不使用下划线也可以将函数作为整体传递给变量
         */
        var f3: () => Int = foo

    }


    /**
     * 2）函数可以作为参数进行传递
     */
    def main2(): Unit ={

        /*
             （1）定义一个函数，函数参数还是一个函数签名；f 表示函数名称;(Int,Int)表示输入两个 Int 参数；Int 表示函数返回值
         */
        def f1(f: (Int, Int) => Int): Int = {
            return f(2, 4)
        }


        /*
            （2）定义一个函数，参数和返回值类型和 f1 的输入参数一致
         */
        def add(a: Int, b: Int): Int = a + b


        /*
            （3）将 add 函数作为参数传递给 f1 函数，如果能够推断出来不是调用，_可以省略
         */
        println(f1(add))
        println(f1(add _))

    }



    /**
     * 3）函数可以作为函数返回值返回
     */
    def main3(): Unit = {

        def f1() = {

            def f2() = {
                println("f2")
            }
            // _ 表示函数整体，作为返回值返回
            f2 _
        }

        /*
             因为 f1 函数的返回值依然为函数，所以可以变量 f 可以作为函数继续调用
         */
        val f = f1()
        f()

        /*
            简化后
         */
        f1()()
    }
}
