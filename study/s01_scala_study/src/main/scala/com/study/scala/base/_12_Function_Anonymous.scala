package com.study.scala.base

/**
 * 匿名函数
 * 1）说明
 *      没有名字的函数就是匿名函数。
 *      (x:Int)=>{函数体}
 *      x：表示输入参数；Int：表示输入参数类型；函数体：表示具体代码逻辑
 *
 *
 */
object _12_Function_Anonymous {


    def main(args: Array[String]): Unit = {

        main3()

    }



    /*
        需求 1：传递的函数有一个参数

        传递匿名函数至简原则：
            （1）参数的类型可以省略，会根据形参进行自动的推导
            （2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过 1 的永远不能省略圆括号。
            （3）匿名函数如果只有一行，则大括号也可以省略
            （4）如果参数只出现一次，则参数省略且后面参数可以用_代替
     */
    def main(): Unit = {

        /*
            （1）定义一个函数：参数包含数据和逻辑函数
         */
        def operation(arr: Array[Int], op: Int => Int) ={
            for (elem <- arr) yield op(elem)
        }


        /*
            （2）定义逻辑函数
         */
        def op(elem: Int): Int = {
            elem + 1
        }


        /*
            （3）标准函数调用
         */
        val arr: Array[Int] = operation(Array(1, 2, 3, 4, 5), op)
        println(arr.mkString(","))


        /*
            （4）将以上的部分使用匿名函数实现
         */
        val arr2: Array[Int] = operation(Array(1, 2, 3, 4, 5), (elem: Int) => {elem + 1})
        println(arr2.mkString(","))


        /*
            （4.1） 参数类型可以省略，可以根据形参进行自动推导
         */
        val arr3: Array[Int] = operation(Array(1, 2, 3, 4, 5), (elem) => {
            elem + 1
        })
        println(arr3.mkString(","))


        /*
             （4.2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过 1 的永远不能省略圆括号。

         */
        val arr4: Array[Int] = operation(Array(1, 2, 3, 4, 5), elem => {
            elem + 1
        })
        println(arr4.mkString(","))


        /*
            (4.3) 匿名函数如果只有一行，则大括号也可以省略
         */
        val arr5: Array[Int] = operation(Array(1, 2, 3, 4, 5), elem => elem + 1)
        println(arr5.mkString(","))


        /*
            （4.4）如果参数只出现一次，则参数省略且后面参数可以用_代替
         */
        val arr6: Array[Int] = operation(Array(1, 2, 3, 4, 5), _ + 1)
        println(arr6.mkString(","))

    }




    /*
        需求 2：传递的函数有两个参数
     */
    def main2(): Unit ={


        /*
            （1）定义一个函数：参数包含数据和逻辑函数
         */
        def calculator(a: Int, b: Int, op: (Int, Int) => Int) = {
            op(a, b)
        }

        /*
            标准版
         */
        val sum = calculator(10, 20, (x: Int, y: Int) => {
            x + y
        })
        println(sum)


        /*
            如果只有一行，则大括号也可以省略
         */
        println(calculator(10, 20, (x: Int, y: Int) => x + y))

        /*
            参数的类型可以省略，会根据形参进行自动的推导;
         */
        println(calculator(10, 20, (x, y) => x + y))


        /*
            如果参数只出现一次，则参数省略且后面参数可以用_代替
         */
        println(calculator(10, 20, _ + _))

    }



    /*
        扩展练习1
        定义一个匿名函数，并将它作为值赋给变量 fun。函数有三个参数，类型分别为 Int，String，Char，返回值类型为 Boolean。
        要求调用函数 fun(0, “”, ‘0’)得到返回值为 false，其它情况均返回 true。
     */
    def main3(): Unit ={

        val fun = (i: Int, s: String, c: Char) =>  { i != 0 || !"".equals(s) || c != '0' }

        println(fun(0, "", '0'))
        println(fun(0, "", '1'))
        println(fun(0, "1", '0'))
    }


    /*
        扩展练习2
        定义一个函数 func，它接收一个 Int 类型的参数，返回一个函数（记作 f1）。
        它返回的函数 f1，接收一个 String 类型的参数，同样返回一个函数（记作 f2）。函数 f2 接收一个 Char 类型的参数，返回一个 Boolean 的值。
        要求调用函数 func(0) (“”) (‘0’)得到返回值为 false，其它情况均返回 true。
     */
    def main4(): Unit ={

        def func(i: Int) = {

            def f1(s: String) = {

                def f2(c: Char) = {
                    i != 0 || !"".equals(s) || c != '0'
                }
                f2 _
            }

            f1 _
        }

        println(func(0)("")('0'))
        println(func(0)("")('1'))
        println(func(0)("1")('0'))
        println(func(1)("")('0'))

    }


}
