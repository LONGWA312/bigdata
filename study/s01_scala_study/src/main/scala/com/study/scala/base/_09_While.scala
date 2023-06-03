package com.study.scala.base

import scala.util.control.Breaks


/**
 * while 和 do...while
 * While和do..While的使用和java语法中的相同
 */
object _09_While {


    def main(args: Array[String]): Unit = {



        /*
            while基本语法：
                循环变量初始化
                while (循环条件) {
                    循环体(语句)
                    循环变量迭代
                }
            说明：
                （1）循环条件是返回一个布尔值的表达式
                （2）while 循环是先判断再执行语句
                （3）与 for 语句不同，while 语句没有返回值，即整个 while 语句的结果是 Unit 类型()
                （4）因为 while 中没有返回值，所以当要用该语句来计算并返回结果时，就不可避免的使用变量，
                    而变量需要声明在 while 循环的外部，那么就等同于循环的内部对外部的变量造成了影响，所以不推荐使用，而是推荐使用 for 循环。
         */
        var i = 0
        while(i < 10){
            println("i = " + i)
            i += 1
        }

        /*
            do..while基本语法
                循环变量初始化;
                do{
                    循环体(语句)
                    循环变量迭代
                } while(循环条件)
            说明：
                （1）循环条件是返回一个布尔值的表达式
                （2）do..while 循环是先执行，再判断
         */
        var j = 0
        do {
            println("i = " + i)
            i += 1
        } while (i < 10)



        /*
            循环中断
                基本说明
                Scala 内置控制结构特地去掉了 break 和 continue，
                是为了更好的适应函数式编程，推荐使用函数式的风格解决break和continue的功能，
                而不是一个关键字。Scala中使用breakable控制结构来实现 break 和 continue 功能。
         */

        /*
            需求 1：采用异常的方式退出循环
         */
        try {
            for (elem <- 1 to 10){
                println("elem = " + elem)
                if (elem == 5) throw new RuntimeException
            }
        } catch {
            case e: RuntimeException =>
        }
        println("正常结束循环")


        /*
            需求 2：采用 Scala 自带的函数，退出循环
         */
        Breaks.breakable(
            for (elem <- 1 to 10) {
                println("elem = " + elem)
                if (elem == 5) Breaks.break()
            }
        )
        println("正常结束循环")


        /*
            需求 3：对 break 进行省略
         */
        import scala.util.control.Breaks._
        breakable {
            for (elem <- 1 to 10){
                println("elem = " + elem)
                if (elem == 5) break()
            }
        }
        println("正常结束循环")


        /*
            需求 4：循环遍历 10 以内的所有数据，奇数打印，偶数跳过（continue）
         */
        for (elem <- 1 to 10){
            if (elem % 2 == 0){
                println("elem = " + elem)
            } else {
                println("continue")
            }
        }



        /*
         多重循环
            基本说明：
               （1）将一个循环放在另一个循环体内，就形成了嵌套循环。其中，for，while，do…while均可以作为外层循环和内层循环。【建议一般使用两层，最多不要超过 3 层】
               （2）设外层循环次数为 m 次，内层为 n 次，则内层循环体实际上需要执行 m*n 次。
         */
        /*
            需求：打印九九乘法表
         */
        for (i <- 1 to 9){
            for (j <- 1 to i){
                print(j + "*" + i + "=" + (i * j) + "\t")
            }
            println()
        }













    }






}
