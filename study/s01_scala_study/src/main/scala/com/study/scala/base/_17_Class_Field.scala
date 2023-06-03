package com.study.scala.base

import scala.beans.BeanProperty


/**
 * 类
 * scala中类的定义
 *
 *      1）基本语法
 *          [修饰符] class 类名 {
 *              类体
 *          }
 *
 *      2）说明
 *          （1）Scala 语法中，类并不声明为 public，所有这些类都具有公有可见性（即默认就是public）
 *          （2）一个 Scala 源文件可以包含多个类
 */


/*
    （1）Scala 语法中，类并不声明为 public，所有这些类都具有公有可见性（即默认就是public）
 */
class Student{

}

/*
    （2）一个 Scala 源文件可以包含多个类
 */
class Teacher{

}


/**
    属性
        属性是类的一个组成部分


    基本语法
        [修饰符] var|val 属性名称 [：类型] = 属性值

    注：Bean 属性（@BeanProperty），可以自动生成规范的 setXxx/getXxx 方法
 */

class Person1{


    var name: String = "bobo"           // 定义一个属性
    var age: Int = _                    //  _表示给属性一个默认值

    @BeanProperty var sex: String = "男"

    //val 修饰的属性不能赋默认值，必须显式的指定
}



object _17_Class {


    def main(args: Array[String]): Unit = {


        var person: Person1 = new Person1()
        println(person.name)
        println(person.age)

        person.setSex("女")
        println(person.getSex)

    }


}
