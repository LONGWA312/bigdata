package com.study.scala.base

/**
 * 递归
 *      一个函数/方法在函数/方法体内又调用了本身，我们称之为递归调用
 *
 *
 *
 * 抽象控制
 *      1）值调用，把计算后的结果传递进去
 *      2）名调用，把代码传递进去
 */
object _15_Function_Recurrence {

    def main(args: Array[String]): Unit = {
        println(test(4))

        main()

        main2()
    }

    /**
        阶乘
        递归算法
            1) 方法调用自身
            2) 方法必须要有跳出的逻辑
            3) 方法调用自身时，传递的参数应该有规律
            4) scala 中的递归必须声明函数返回值类型
     */
    def test(i: Int): Int ={
        if (i == 1){
            i
        } else {
            i * test(i - 1)
        }

    }


    /**
     * 抽象控制
     *      1）值调用，把计算后的结果传递进去
     *      2）名调用，把代码传递进去
     */
    /*
        1）值调用，把计算后的结果传递进去
     */
    def foo(a: Int): Unit = {
        println(a)
        println(a)
    }

    def main(): Unit = {


        def f = () => {
            println("f....")
            10
        }

        foo(f())
    }


    /*
        2）名调用，把代码传递进去
     */
    def foo2(a: => Int): Unit ={
        println(a)
        println(a)
    }

    def main2(): Unit = {

        def f = () => {
            println("f....")
            10
        }

        foo2(f())
    }


    /**

        案例
        比较看不懂

     */
    def foo3(a: => Unit): Unit ={
        println(a)
        println(a)
    }
    def main3(): Unit ={
        // （1）传递代码块
        foo3({
            println("aaa")
        })


        // （2）小括号可以省略
        foo3{
            println("aaa")
        }
    }



    /**
        自定义一个while循环
     */
    def myWhile(condition: => Boolean)(op: => Unit): Unit ={
        if (condition){
            op
            myWhile(condition)(op)
        }
    }
    def main4(): Unit ={

        var i: Int = 1
        myWhile(i < 10) {
            println("i = " + i)
            i += 1
        }
    }












}
