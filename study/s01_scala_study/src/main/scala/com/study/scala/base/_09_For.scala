package com.study.scala.base

import scala.collection.immutable


/*
for循环控制

    Scala 也为 for 循环这一常见的控制结构提供了非常多的特性，这些 for 循环的特性被称为 for 推导式或 for 表达式。


 */
object _09_For {


    def main(args: Array[String]): Unit = {


        /*
            范围数据循环 (To)
            （1）i 表示循环的变量，<- 规定 to
            （2）i 将会从 1-3 循环，前后闭合
         */
        for(i <- 1 to 3){
            println("i=" + i)
        }



        /*
            范围数据循环 （until）
            （1）这种方式和前面的区别在于 i 是从 1 到 3-1
            （2）即使前闭合后开的范围

         */
        for(i <- 1 until 3){
            println("i=" + i)
        }



        /*
            循环守卫
            （1）循环守卫，即循环保护式（也称条件判断式，守卫）。保护式为 true 则进入循环体内部，为 false 则跳过，类似于 continue。
            （2）上面的代码等价
         */
        for(i <- 1 to 3 if i != 2){
            println("i=" + i)
        }
        //等价于
        for(i <- 1 to 3){
            if (i != 2){
                println("i=" + i)
            }
        }



        /*
            循环步长
            by 表示步长
         */
        for(i <- 1 to 10 by 2){
            println("i=" + i)
        }



        /*
            嵌套循环
            没有关键字，所以范围后一定要加；来隔断逻辑
         */
        for(i <- 1 to 3; j <- 1 to 3){
            println("i = " + i + " j = " + j)
        }
        //等价于
        for(i <- 1 to 3){
            for(j <- 1 to 3){
                println("i = " + i + " j = " + j)
            }
        }



        /*
            引入变了
            （1）for 推导式一行中有多个表达式时，所以要加 ; 来隔断逻辑
            （2）for 推导式有一个不成文的约定：当 for 推导式仅包含单一表达式时使用圆括号，当包含多个表达式时，一般每行一个表达式，并用花括号代替圆括号，
         */
        for(i <- 1 to 3; j = 4 - i){
            println("i = " + i + " j = " + j)
        }
        //等价于
        for{
            i <- 1 to 3
            j = 4 - i
        } {
            println("i = " + i + " j = " + j)
        }
        // 等价于
        for(i <- 1 to 3){
            var j = 4 - i
            println("i = " + i + " j = " + j)
        }




        /*
            循环返回值
            将遍历过程中处理的结果返回到一个新 Vector 集合中，使用 yield 关键字。
            Vector(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
         */
        val res: immutable.Seq[Int] = for(i <- 1 to 10) yield {
            i * 2
        }
        println(res)



        /*
            倒序打印
            使用reverse
         */
        for(i <- 1 to 10 reverse){
            println(i)
        }

    }

}
