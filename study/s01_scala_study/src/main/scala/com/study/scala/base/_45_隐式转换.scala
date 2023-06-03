package com.study.scala.base

/**
 * 隐式转换
    当编译器第一次编译失败的时候，会在当前的环境中查找能让代码编译通过的方法，用于将类型进行转换，实现二次编译

 */


/**
 * 隐式函数
 * 隐式转换可以在不需改任何代码的情况下，扩展某个类的功能。
 *
 */
/*
    需求：通过隐式转化为 Int 类型增加方法。
 */
class MyRichInt(val self: Int) {

    def myMax(i: Int): Int = {
        if (self < i) i else self
    }

    def myMin(i: Int): Int = {
        if (self > i) self else i
    }

}

object TesttImplicitFunction {


    /*
        使用 implicit 关键字声明的函数称之为隐式函数
     */
    implicit def convert(arg: Int): MyRichInt = {
        new MyRichInt(arg)
    }


    /*
        当想调用对象功能时，如果编译错误，那么编译器会尝试在当前作用域范围内查找能调用对应功能的转换规则，
        这个调用过程是由编译器完成的，所以称之为隐式转换。也称之为自动转换
     */
    def main(args: Array[String]): Unit = {
        println(2.myMax(6))
    }
}


/**
 * 隐式参数
    普通方法或者函数中的参数可以通过 implicit 关键字声明为隐式参数，调用该方法时，就可以传入该参数，编译器会在相应的作用域寻找符合条件的隐式值。
    1）说明
        （1）同一个作用域中，相同类型的隐式值只能有一个
        （2）编译器按照隐式参数的类型去寻找对应类型的隐式值，与隐式值的名称无关。
        （3）隐式参数优先于默认参数

 */
object TestImplicitParameter{

    implicit val str: String = "implicit string"

    def hello(implicit arg: String = "default string"): Unit = {
        println(arg)
    }

    def main(args: Array[String]): Unit = {
        hello
    }
}


/**
 * 隐式类
    在 Scala2.10 后提供了隐式类，可以使用 implicit 声明类，隐式类的非常强大，同样可以扩展类的功能，在集合中隐式类会发挥重要的作用。

 * 说明：
    （1）其所带的构造参数有且只能有一个
    （2）隐式类必须被定义在“类”或“伴生对象”或“包对象”里，即隐式类不能是顶级的。
 */
object TestImplicitClass {

    implicit class MyRichInt(arg: Int) {

        def myMax(i: Int): Int = {
            if (arg < i) i else arg
        }

        def myMin(i: Int): Int = {
            if (arg > i) arg else arg
        }

    }

    def main(args: Array[String]): Unit = {
        println(1.myMax(6))
    }
}


/**
 * 隐式解析机制

 1）说明
    （1）首先会在当前代码作用域下查找隐式实体（隐式方法、隐式类、隐式对象）。（一般是这种情况）
    （2）如果第一条规则查找隐式实体失败，会继续在隐式参数的类型的作用域里查找。类型的作用域是指与该类型相关联的全部伴生对象以及该类型所在包的包对象。
 */


