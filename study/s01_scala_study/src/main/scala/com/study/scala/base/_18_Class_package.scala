package com.study.scala.base

/**

    封装
        封装就是把抽象出的数据和对数据的操作封装在一起，数据被保护在内部，程序的其它部分只有通过被授权的操作（成员方法），才能对数据进行操作。
        Java 封装操作如下，
            （1）将属性进行私有化
            （2）提供一个公共的 set 方法，用于对属性赋值
            （3）提供一个公共的 get 方法，用于获取属性的值

        Scala 中的 public 属性，底层实际为 private，并通过 get 方法（obj.field()）和 set 方法（obj.field_=(value)）对其进行操作。
        所以 Scala 并不推荐将属性设为 private，再为其设置public 的 get 和 set 方法的做法。
        但由于很多 Java 框架都利用反射调用 getXXX 和 setXXX 方法，有时候为了和这些框架兼容，
        也会为 Scala 的属性设置 getXXX 和 setXXX 方法（通过@BeanProperty 注解实现）。


    访问权限
        在 Java 中，访问权限分为：public，private，protected 和默认。
        在 Scala 中，你可以通过类似的修饰符达到同样的效果。但是使用上有区别。
            （1）Scala 中属性和方法的默认访问权限为 public，但 Scala 中无 public 关键字。
            （2）private 为私有权限，只在类的内部和伴生对象中可用。
            （3）protected 为受保护权限，Scala 中受保护权限比 Java 中更严格，同类、子类可以访问，同包无法访问。
            （4）private[包名]增加包访问权限，包名下的其他类也可以使用


 */

class Person2{


    private var name: String = "bobo"
    protected var age: Int = 20
    private[base] var sex: String = "男"

    def say(): Unit ={
        println("say: " + name)
    }


}


/**
 *  object Person2为伴生对象
 */
object Person2 {

    def main(args: Array[String]): Unit = {

        val person2: Person2 = new Person2

        person2.say()
        println(person2.name)
        println(person2.age)

    }
}

class Teacher2 extends Person2{

    def test(): Unit ={

        //println(this.name)        // private 为私有权限，只在类的内部和伴生对象中可用。子类中设置
        println(this.age)
        println(this.sex)
    }
}


class Animal{

    def test: Unit={
        println(new Person2().sex)
    }

}