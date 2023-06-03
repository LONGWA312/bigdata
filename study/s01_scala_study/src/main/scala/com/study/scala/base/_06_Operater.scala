package com.study.scala.base

/*
    运算符

    object TestArithmetic {
 def main(args: Array[String]): Unit = {


 */
object _06_Operater {


    /**
     * 算术运算符
     */
    def testArithmetic(): Unit = {

        //（1）对于除号“/”，它的整数除和小数除是有区别的：整数之间做除法时，只保留整数部分而舍弃小数部分。
        var r1: Int = 10 / 3 // 3
        println("r1=" + r1)
        var r2: Double = 10 / 3 // 3.0
        println("r2=" + r2)
        var r3: Double = 10.0 / 3 // 3.3333
        println("r3=" + r3)
        println("r3=" + r3.formatted("%.2f")) // 含义：保留小数点 2位，使用四舍五入

    }



    /**
     * 关系比较运算符
     */
    def testRelation(): Unit = {

        // 测试：>、>=、<=、<、==、!=
        var a: Int = 2
        var b: Int = 1
        println(a > b) // true
        println(a >= b) // true
        println(a <= b) // false
        println(a < b) // false
        println("a==b" + (a == b)) // false
        println(a != b) // true

    }

    /**
     * 在scala中， ==更加类似于 Java 中的 equals
     */
    def testRelation2(): Unit = {

        val s1 = "abc"
        val s2 = new String("abc")
        println(s1 == s2)
        println(s1.eq(s2))
    }

    /**
     * 赋值运算符
     */
    def testAssignment(): Unit = {
        /**
         * Scala 中没有++、--操作符，可以通过+=、-=来实现同样的效果；
         */
        var r1 = 10

        r1 += 1
        println(r1)

        r1 -= 1
        println(r1)

    }

    /**
     * 位运算符
     */
    def testPosition(): Unit = {

        var n1: Int = 8
        n1 = n1 << 2
        println(n1)

    }


    /**
     * scala运算符的本质
     * 在scala中，其实是没有运算符的，所有运算符的本质都是函数
     * 1）当调用对象的方法时，点.可以省略
     * 2）如果函数参数只有一个，或者没有参数，()可以省略
     */
    def testOpt(): Unit = {

        // 标准的加法运算
        val i:Int = 1.+(1)
        // （1）当调用对象的方法时，.可以省略
        val j:Int = 1 + (1)
        // （2）如果函数参数只有一个，或者没有参数，()可以省略
        val k:Int = 1 + 1

        println(1.toString())
        println(1 toString())
        println(1 toString)


    }

    def main(args: Array[String]): Unit = {

        testRelation2()
        testPosition()
    }

}
