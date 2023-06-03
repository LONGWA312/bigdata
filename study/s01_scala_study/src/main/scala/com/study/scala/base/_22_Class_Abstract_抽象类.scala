package com.study.scala.base

/**
 * 抽象类
 *
 * 抽象属性和抽象方法

        1) 基本语法
            （1）定义抽象类：abstract class Person{} //通过 abstract 关键字标记抽象类
            （2）定义抽象属性：val|var name:String //一个属性没有初始化，就是抽象属性
            （3）定义抽象方法：def hello():String //只声明而没有实现的方法，就是抽象方法


        2）重写&继承
            （1）如果父类为抽象类，那么子类需要将抽象的属性和方法实现，否则子类也需声明为抽象类
            （2）重写非抽象方法需要用 override 修饰，重写抽象方法则可以不加 override。
            （3）子类中调用父类的方法使用 super 关键字
            （4）子类对抽象属性进行实现，父类抽象属性可以用 var 修饰；
                    子类对非抽象属性重写，父类非抽象属性只支持 val 类型，而不支持 var。
                    因为 var 修饰的为可变变量，子类继承之后就可以直接使用，没有必要重写


        3) 匿名子类
            和 Java 一样，可以通过包含带有定义或重写的代码块的方式创建一个匿名的子类。
 *
 */

/*
    定义抽象，抽象属性，抽象方法
    （1）定义抽象类：abstract class Person{} //通过 abstract 关键字标记抽象类
    （2）定义抽象属性：val|var name:String //一个属性没有初始化，就是抽象属性
    （3）定义抽象方法：def hello():String //只声明而没有实现的方法，就是抽象方法
 */
abstract class Person9{

    val name: String

    def hello(): Unit

}

/*
    抽象类的实现
 */
class Teacher9 extends Person9{

    override val name: String = "Teacher"

    override def hello(): Unit = {
        println("hello teacher")
    }
}





/*
    3) 匿名子类
            和 Java 一样，可以通过包含带有定义或重写的代码块的方式创建一个匿名的子类。
 */
abstract class Person10{

    val name: String

    def hello(): Unit
}


object Test10{

    def main(args: Array[String]): Unit = {

        val person = new Person10{

            override val name: String = "teacher"

            override def hello(): Unit = {
                println("hello teacher")
            }
        }

        println(person.name)
        person.hello()
    }
}


