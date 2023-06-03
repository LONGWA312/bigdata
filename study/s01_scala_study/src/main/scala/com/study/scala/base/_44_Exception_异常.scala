package com.study.scala.base

/**
 * 异常处理
    1）我们将可疑代码封装在 try 块中。在 try 块之后使用了一个 catch 处理程序来捕获异常。如果发生任何异常，catch 处理程序将处理它，程序将不会异常终止。
    2）Scala 的异常的工作机制和 Java 一样，但是 Scala 没有“checked（编译期）”异常，即 Scala 没有编译异常这个概念，异常都是在运行的时候捕获处理。
    3）异常捕捉的机制与其他语言中一样，如果有异常发生，catch 子句是按次序捕捉的。因此，在 catch 子句中，越具体的异常越要靠前，越普遍的异常越靠后，
        如果把越普遍的异常写在前，把具体的异常写在后，在 Scala 中也不会报错，但这样是非常不好的编程风格。
    4）finally 子句用于执行不管是正常处理还是有异常发生时都需要执行的步骤，一般用于对象的清理工作，这点和 Java 一样。
    5）用 throw 关键字，抛出一个异常对象。所有异常都是 Throwable 的子类型。throw 表达式是有类型的，就是 Nothing，因为 Nothing 是所有类型的子类型，
        所以 throw 表达式可以用在需要类型的地方
 */
object _44_Exception {

    def main(args: Array[String]): Unit = {

        try {
            var n = 10 /0
        } catch {
            case ex: ArithmeticException => {
                println("发送算术异常")
            }
            case ex: Exception => {
                println("发送异常了")
            }
        } finally {
            println("finally")
        }

    }


    def testThrowExcept(): Nothing = {
        throw new Exception("抛出一个异常")
    }


    /*
        6）java 提供了 throws 关键字来声明异常。可以使用方法定义声明异常。它向调用者函数提供了此方法可能引发此异常的信息。
        它有助于调用函数处理并将该代码包含在 try-catch块中，以避免程序异常终止。在 Scala 中，可以使用 throws 注解来声明异常
     */
    @throws(classOf[NumberFormatException])
    def f11() = {
        "abc".toInt
    }

}
