package com.study.scala.base

/**
 * 单例对象（伴生对象）

        Scala语言是完全面向对象的语言，所以并没有静态的操作（即在Scala中没有静态的概念）。
        但是为了能够和Java语言交互（因为Java中有静态概念），就产生了一种特殊的对象来模拟类对象，该对象为单例对象。
        若单例对象名与类名一致，则称该单例对象这个类的伴生对象，这个类的所有“静态”内容都可以放置在它的伴生对象中声明。


 * 基本语法

        object Person{
            val country: String = "China"
        }

 * 说明
        （1）单例对象采用 object 关键字声明
        （2）单例对象对应的类称之为伴生类，伴生对象的名称应该和伴生类名一致。
        （3）单例对象中的属性和方法都可以通过伴生对象名（类名）直接调用访问。


 *
 */


/*
    （1）伴生对象采用 object 关键字声明
 */
object Person11 {

    var name: String = "bobo1"

}

/*
    （2）伴生对象对应的类称之为伴生类，伴生对象的名称应该和伴生类名一致。
 */
class Person11 {

    val name: String = "bobo"
}


object TestTrait2 {

    def main(args: Array[String]): Unit = {

        /*
            （3）伴生对象中的属性和方法都可以通过伴生对象名（类名）直接调用访问。
         */
        println(Person11.name)

    }
}
