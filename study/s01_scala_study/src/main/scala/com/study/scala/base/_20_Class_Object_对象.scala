package com.study.scala.base

/**
基本语法：
    val | var 对象名 [：类型] = new 类型()

 注意：
    （1）val 修饰对象，不能改变对象的引用（即：内存地址），可以改变对象属性的值。
    （2）var 修饰对象，可以修改对象的引用和修改对象的属性值
    （3）自动推导变量类型不能多态，所以多态需要显示声明
 */

class Person4{
    var name: String = "cang"
}

object Person4{

    def main(args: Array[String]): Unit = {

        /*
            （1）val 修饰对象，不能改变对象的引用（即：内存地址），可以改变对象属性的值。
         */

        val person: Person4 = new Person4()
        person.name = "bobo"

        println(person.name)
    }

}