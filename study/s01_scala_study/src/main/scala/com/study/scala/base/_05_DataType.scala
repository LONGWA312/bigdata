package com.study.scala.base

/*
 数据类型

 1. Java数据类型
    java基本类型：char、byte、short、int、long、float、double、boolean
    java基本类型的包装类：Character、Byte、Short、Integer、Long、Float、Double、Boolean
    注意：Java中基本类型和引用类型没有共同的祖先。


 2. Scala数据类型
    1）Scala中一切数据都是对象，都是Any的子类。
    2）Scala中数据类型分为两大类：数值类型（AnyVal）、引用类型（AnyRef），不管是值类型还是引用类型都是对象。
    3）Scala数据类型仍然遵守，低精度的值类型向高精度值类型，自动转换（隐式转换）
    4）Scala中的StringOps是对Java中的String增强
    5）Unit：对应Java中的void，用于方法返回值的位置，表示方法没有返回值。Unit是 一个数据类型，只有一个对象就是()。Void不是数据类型，只是一个关键字
    6）Null是一个类型，只 有一个对 象就 是null。它是所有引用类型（AnyRef）的子类。
    7）Nothing，是所有数据类型的子类，主要用在一个函数没有明确返回值时使用，因为这样我们可以把抛出的返回值，返回给任何的变量或者函数。

    整数类型：
        Byte: 8
        Short: 16
        Int: 32
        Long: 64

    浮点类型：
        Float:
        Double:
        Scala 的浮点型常量默认为 Double 型，声明 Float 型常量，须后加‘f’或‘F’。

    字符类型：
        字符类型可以表示单个字符，字符类型是 Char。

    布尔类型：
        1）布尔类型也叫 Boolean 类型，Booolean 类型数据只允许取值 true 和 false
        2）boolean 类型占 1 个字节。
 */

/*

    重点：

    Unit类型：
        表示无值，和其他语言中 void 等同。用作不返回任何结果的方法的结果类型。Unit 只有一个实例值，写成()。

    Null类型：
        null , Null类型只有一个实例值 null

    Nothing类型:
        Nothing 类型在 Scala 的类层级最低端；它是任何其他类型的子类型。当一个函数，我们确定没有正常的返回值，可以用 Nothing 来指定返回类型，
        这样有一个好处，就是我们可以把返回的值（异常）赋给其它的函数或者变量（兼容性）


 */


class Cat(){

}

object _05_DataType {

    def main(args: Array[String]): Unit = {


        /**
         *表示无值，和其他语言中 void 等同。用作不返回任何结果的方法的结果类型。Unit 只有一个实例值，写成()。
         */
        def sayOk: Unit = {

        }

        println(sayOk)



        /**
         * Null 类只有一个实例对象，Null 类似于 Java 中的 null 引用。Null 可以赋值给任
         * 意引用类型（AnyRef），但是不能赋值给值类型（AnyVal）
         */
        var cat = new Cat()
        println(cat)

        cat = null
        println(cat)

//        var n1: Int = null      // 错误
//        println("n1: " + n1)




        /**
         * Nothing，可以作为没有正常返回值的方法的返回类型，非常直观的告诉你这个方法不会正常返回，
         * 而且由于 Nothing 是其他任意类型的子类，他还能跟要求返回值的方法兼容。
         */
        def test() : Nothing = {
            throw new Exception()
        }

//        test()


        testForceTransfer


        testIntToByte


    }


    /*
    数值类型的自动转换

        当 Scala 程序在进行赋值或者运算时，精度小的类型自动转换为精度大的数值类型，这个就是自动类型转换（隐式转换）。
        （1）自动提升原则：有多种类型的数据混合运算时，系统首先自动将所有数据转换成精度大的那种数据类型，然后再进行计算。
        （2）把精度大的数值类型赋值给精度小的数值类型时，就会报错，反之就会进行自动类型转换。
        （3）（byte，short）和 char 之间不会相互自动转换。
        （4）byte，short，char 他们三者可以计算，在计算时首先转换为 int 类型。


    强制类型转换
        自动类型转换的逆过程，将精度大的数值类型转换为精度小的数值类型。使用时要加上强制转函数，但可能造成精度降低或溢出，格外要注意。

    */
    def testForceTransfer: Unit ={


        /**
         * 将数据由高精度，向低精度转换就需要使用进行强制类型转换
         */
        var n1: Int = 2.5.toInt
        print("n1="+n1)


        /**
         * 强转符只针对于最近的操作数对象有效，往往会使用小括号提高优先级
         */

        var r1: Int = 10 * 3.5.toInt + 6 * 1.5.toInt    // 10 *3 + 6*1= 36
        var r2: Int = (10 * 3.5 + 6 * 1.5).toInt        // 44.0.toInt =44
        println("r1=" + r1 + " r2=" + r2)

    }


    /*
        基本类型和String类型之间的转换
     */
    def transforForString: Unit = {

        /**
         * 基本类型转String
         */
        var s1 : String = true + ""
        var s2 : String = 4.5 + ""
        var s3 : String = 100 + ""


        /**
         * String类型转基本的数值类型
         */
        val s : String = "12"
        var n1 : Byte = s.toByte
        var n2 : Short = s.toShort
        var n3 : Int = s.toInt
        var n4 : Long = s.toLong

    }


    /*
        面试扩展
     */
    def testIntToByte: Unit = {

        //        var n1: Int = 128
        var n: Int = 130
        var b: Byte = n.toByte

        println(b)



    }



}
