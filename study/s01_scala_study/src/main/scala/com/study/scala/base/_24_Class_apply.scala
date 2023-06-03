package com.study.scala.base

/**
 * apply方法
 *
 *  说明

        （1）通过伴生对象的 apply 方法，实现不使用 new 方法创建对象。
        （2）如果想让主构造器变成私有的，可以在()之前加上 private。
        （3）apply 方法可以重载。
        （4）Scala 中 obj(arg)的语句实际是在调用该对象的 apply 方法，即 obj.apply(arg)。用以统一面向对象编程和函数式编程的风格。
        （5）当使用 new 关键字构建对象时，调用的其实是类的构造方法，当直接使用类名构建对象时，调用的其实时伴生对象的 apply 方法。

 *  注意：
        也可以创建其它类型对象，并不一定是伴生类对象
 *
 */
object Test12 {

    def main(args: Array[String]): Unit = {
        /*
            通过伴生对象的 apply 方法，实现不使用 new 方法创建对象。
         */
        val p1 = Person12()
        println("p1.name = " + p1.name)

        val p2 = Person12("bobo")
        println("p2.name = " + p2.name)
    }
}

/*
    如果想让主构造器变成私有的，可以在()之前加上 private。
 */
class Person12 private(cName: String) {

    val name: String = cName
}


object Person12 {

    def apply(): Person12 = {

        println("apply空参被调用")
        new Person12("xx")
    }

    def apply(cName: String): Person12 = {
        println("app有参数被调用")
        new Person12(cName)
    }


}
