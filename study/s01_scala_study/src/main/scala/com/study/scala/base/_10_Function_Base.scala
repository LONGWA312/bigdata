package com.study.scala.base

/**
 1）面向对象编程
    解决问题，分解对象，行为，属性，然后通过对象的关系以及行为的调用来解决问题。
    对象：用户
    行为：登录、连接 JDBC、读取数据库
    属性：用户名、密码

    Scala 语言是一个完全面向对象编程语言。万物皆对象
    对象的本质：对数据和行为的一个封装


 2）函数式编程
    解决问题时，将问题分解成一个一个的步骤，将每个步骤进行封装（函数），通过调用这些封装好的步骤，解决问题。
    例如：请求->用户名、密码->连接 JDBC->读取数据库
    Scala 语言是一个完全函数式编程语言。万物皆函数。
    函数的本质：函数可以当做一个值进行传递


 3）在 Scala 中函数式编程和面向对象编程完美融合在一起了。




 */
object _10_Function_Base {

    /*
        函数基础语法
            def sum(x: Int, y: Int) : Int = {
                x + y
            }
     */


    def main(args: Array[String]): Unit = {

        /*
            需求：定义一个函数，实现将传入的名称打印出来。
         */
        def func(arg: String): Unit = {
            println("arg = " + arg)
        }

        func("Hello scala")


        /*
            调用重载的main方法
         */
        main()


        /*
            函数的定义
            （1）函数 1：无参，无返回值
            （2）函数 2：无参，有返回值
            （3）函数 3：有参，无返回值
            （4）函数 4：有参，有返回值
            （5）函数 5：多参，无返回值
            （6）函数 6：多参，有返回值
         */

        def test1(): Unit = {
            println("（1）函数 1：无参，无返回值")
        }
        test1()


        def test2(): String = {
            return "（2）函数 2：无参，有返回值"
        }
        println(test2())


        def test3(s: String): Unit = {
            println(s)
        }
        println(test3("（3）函数 3：有参，无返回值"))


        def test4(s: String): String = {
            return " 4：有参，有返回值"
        }
        println(test4("（4）函数"))


        def test5(name: String, age: Int): Unit ={
            println(s"$name, $age")
        }
        test5("TT", 20)


        def test6(x: Int, y: Int): Int = {
            return x + y
        }
        test6(1, 2)




        /*
            函数参数
            （1）可变参数
            （2）如果参数列表中存在多个参数，那么可变参数一般放置在最后
            （3）参数默认值，一般将有默认值的参数放置在参数列表的后面
            （4）带名参数
         */
        /*
            可变参数
         */
        def test7(s: String*): Unit ={
            println(s)
        }
        // 有参输入，输出array
        test7("hello", "scala")

        //无参输入， 输出List
        test7()

        /*
            (2)如果参数列表中存在多个参数，那么可变参数一般放置在最后
         */
        def test8( name : String, s: String* ): Unit = {
            println(name + "," + s)
        }
        test8("jinlian", "dalang")

        /*
            参数默认值
         */
        def test9(name: String, age: Int = 10): Unit = {
            println(s"$name, $age")
        }

        // 如果参数传递了值，那么会覆盖默认值
        test9("jinlian", 20)

        // 如果参数有默认值，在调用的时候，可以省略这个参数
        test9("dalang")


        /*
            一般情况下，将有默认值的参数放置在参数列表的后面
         */
        def test10( sex : String = "男", name : String ): Unit = {
            println(s"$name, $sex")
        }

        // Scala 函数中参数传递是，从左到右
        //test10("wusong")

        //（4）带名参数
        test10(name="wusong")





        /*

        函数至简原则（重点）
        函数至简原则：能省则省
            1）至简原则细节
            （1）return 可以省略，Scala 会使用函数体的最后一行代码作为返回值
            （2）如果函数体只有一行代码，可以省略花括号
            （3）返回值类型如果能够推断出来，那么可以省略（:和返回值类型一起省略）
            （4）如果有 return，则不能省略返回值类型，必须指定
            （5）如果函数明确声明 unit，那么即使函数体中使用 return 关键字也不起作用
            （6）Scala 如果期望是无返回值类型，可以省略等号
            （7）如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可不加
            （8）如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
            （9）如果不关心名称，只关心逻辑处理，那么函数名（def）可以省略

        */
        /*
            标准写法
         */
        def f(s: String): String = {
            return s + "123"
        }
        println(f("hello "))

        /*
            （1）return 可以省略，Scala 会使用函数体的最后一行代码作为返回值
         */
        def f1(s: String): String = {
            s + "123"
        }
        println(f1("hello "))

        /*
            （2）如果函数体只有一行代码，可以省略花括号
         */
        def f2(s: String): String = s + "123"
        println(f2("hello "))

        /*
            （3）返回值类型如果能够推断出来，那么可以省略（:和返回值类型一起省略）
         */
        def f3(s: String) = s + "123"
        println(f3("hello "))

        /*
            （4）如果有 return，则不能省略返回值类型，必须指定
         */
        def f4(): String = {
            return "hello"
        }
        println(f4())


        /*
            （5）如果函数明确声明 unit，那么即使函数体中使用 return 关键字也不起作用
         */
        def f5(): Unit = {
            return "hello"
        }
        println(f5())

        /*
            （6）Scala 如果期望是无返回值类型，可以省略等号
            无返回的函数称之为过程
         */
        def f6() {
            "hello"
        }
        println(f6())

        /*
            （7）如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可不加
         */
        def f7() = "hello"
        println(f7)
        println(f7())

        /*
             （8）如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
         */
        def f8 = "hello"
        println(f8)


        /*
            （9）如果不关心名称，只关心逻辑处理，那么函数名（def）可以省略
         */
        def f9 = (x: String) => {println("hello")}

        def f10(f:String => Unit) = {
            f("")
        }
        f10(f9)
        println(f10((x: String) => {println("hello")}))

    }


    /*
        函数和方法的区别
        1）核心概念
            （1）为完成某一功能的程序语句的集合，称为函数。
            （2）类中的函数称之方法。

        2）注意
            （1）Scala 语言可以在任何的语法结构中声明任何的语法
            （2）函数没有重载和重写的概念；方法可以进行重载和重写
            （3）Scala 中函数可以嵌套定义
     */
    // (2)方法可以进行重载和重写，程序可以执行
    def main(): Unit = {

        /*
            （1）Scala 语言可以在任何的语法结构中声明任何的语法
         */
        import java.util.Date
        new Date()

        /*
             (2)函数没有重载和重写的概念，程序报错
         */
        def test(): Unit = {
            println("无参， 无返回值")
        }
        test()

        /*
        def test(): Unit = {
            println("无参， 无返回值")
        }
         */

        /*
            （3）Scala 中函数可以嵌套定义
         */
        def test2(): Unit = {

            def test3(): Unit = {
                println("函数可以定义嵌套")
            }
        }

    }





}
