package com.study.scala.base

/**
 * 柯里化 和 闭包
 *
 *  闭包：
 *      如果一个函数，访问到了它的外部（局部）变量的值，那么这个函数和他所处的环境，称为闭包
 *
 *
 *  柯里化：
 *      把一个参数列表的多个参数，变成多个参数列表。
 *
 *
 *
 */
object _14_Function_Currying_Closure {


    def main(args: Array[String]): Unit = {

        /*
            闭包
         */
        def f1() = {
            val a: Int = 10
            def f2(b: Int): Int = {
                a + b
            }
            f2 _
        }


        /*
            在调用时，f1 函数执行完毕后，局部变量 a 应该随着栈空间释放掉
         */
        val f = f1()


        /*
            但是在此处，变量 a 其实并没有释放，而是包含在了 f2 函数的内部，形成了闭合的效果
         */
        println(f(3))
        println(f1()(3))



        /*
            函数柯里化，其实就是将复杂的参数逻辑变得简单化,函数柯里化一定存在闭包
         */
        val a = 10
        def f3()(b: Int) ={
            a + b
        }
        println(f3()(3))


        /*
            柯里化： 指的是将原来接受多个参数的函数变成新的接受一个参数的函数的过程，
            柯里化后内层函数一定会使用到外层函数的变量，所以柯里化的过程一定存在闭包
         */
        //柯里化前： 三个参数
        //def addMulti(a:Int,b:Int,c:Int) =(a+b)*c


        //柯里化后一个参数
        def addMulti(a:Int)={
            (b:Int)=>(c:Int)=>(a+b)*c
        }
        def main(args: Array[String]): Unit = {
            println(addMulti(10)(20)(30))
        }



    }

}
