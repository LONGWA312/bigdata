package com.study.scala.base

/**
 *  构造器
        和 Java 一样，Scala 构造对象也需要调用构造方法，并且可以有任意多个构造方法。Scala 类的构造器包括：主构造器和辅助构造器

 *  基本语法
        class 类名(形参列表) { // 主构造器
                //类体
                def this(形参列表) {// 辅助构造器

                }
                def this(形参列表) { //辅助构造器可以有多个...

                }
        }
        说明：
                （1）辅助构造器，函数的名称 this，可以有多个，编译器通过参数的个数及类型来区分。
                （2）辅助构造方法不能直接构建对象，必须直接或者间接调用主构造方法。
                （3）构造器调用其他另外的构造器，要求被调用构造器必须提前声明。


 *  构造器参数
        1）说明
                Scala 类的主构造器函数的形参包括三种类型：未用任何修饰、var 修饰、val 修饰
                （1）未用任何修饰符修饰，这个参数就是一个局部变量
                （2）var 修饰参数，作为类的成员属性使用，可以修改
                （3）val 修饰参数，作为类只读属性使用，不能修改



 *  继承和多态

        1）基本语法
                class 子类名 extends 父类名 {
                        类体
                }

                （1）子类继承父类的属性和方法
                （2）scala 是单继承

        2）注意：
                （1）子类继承父类的属性和方法
                （2）继承的调用顺序：父类构造器->子类构造器

 *  动态绑定
        Scala 中属性和方法都是动态绑定，而 Java 中只有方法为动态绑定。

 *
 */



/*
  （1）如果主构造器无参数，小括号可省略，构建对象时调用的构造方法的小括号也可以省略
 */
class Person5(){

        var name: String = _
        var age: Int = _

        /*
          （2）辅助构造方法不能直接构建对象，必须直接或者间接调用主构造方法。
         */
        def this(age: Int){
                this()
                this.age = age
                println("辅助构造器")
        }


        /*
          （3）构造器调用其他另外的构造器，要求被调用构造器必须提前声明。
         */
        def this(name: String, age: Int){
                this(age)
                this.name = name
                println("辅助构造器")
        }

        println("主构造器")

}

object Person5{

        def main(args: Array[String]): Unit = {
                val person = new Person5(20)
        }

}


/**
 *
 * 构造器参数
        1）说明
                Scala 类的主构造器函数的形参包括三种类型：未用任何修饰、var 修饰、val 修饰
                （1）未用任何修饰符修饰，这个参数就是一个局部变量
                （2）var 修饰参数，作为类的成员属性使用，可以修改
                （3）val 修饰参数，作为类只读属性使用，不能修改

 */

class Person6(name: String, var age: Int, val sex: String){

}


object Person6{

        def main(args: Array[String]): Unit = {

                val person6 = new Person6("bobo", 18, "男")

                /*
                        （1）未用任何修饰符修饰，这个参数就是一个局部变量
                 */
                println(person6)


                /*
                        （2）var 修饰参数，作为类的成员属性使用，可以修改
                 */
                person6.age = 20
                println(person6.age)


                /*
                        （3）val 修饰参数，作为类只读属性使用，不能修改
                 */
                //person6.sex = "nv"
                println(person6.sex)

        }

}


/**

 *  继承和多态

        1）基本语法
                class 子类名 extends 父类名 {
                        类体
                }

                （1）子类继承父类的属性和方法
                （2）scala 是单继承

        2）注意：
                （1）子类继承父类的属性和方法
                （2）继承的调用顺序：父类构造器->子类构造器

 */
class Person7(nameParam: String){

        var name: String = nameParam
        var age: Int = _


        def this(nameParam: String, ageParam: Int){
                this(nameParam)
                this.age = age
                println("父类辅助构造器")
        }

        println("父类主构造器")

}


class Emp(nameParam: String, ageParam: Int) extends Person7(nameParam, ageParam){

        var empNo: Int = _

        def this(nameParam: String, ageParam: Int, empNo: Int){
                this(nameParam, ageParam)
                this.empNo = empNo
                println("子类的辅助构造器")
        }

        println("子类的主构造器")
}


object Person7{

        def main(args: Array[String]): Unit = {
                new Emp("zz", 23, 10001)
        }
}




/**
 *  动态绑定

        Scala 中属性和方法都是动态绑定，而 Java 中只有方法为动态绑定。
 */

class Person8{

        val name: String = "person"

        def hello(): Unit ={
                println("hello person")
        }
}


class Teacher8 extends Person8{

        override val name: String = "Teacher"

        override def hello(): Unit = {
                println("hello teacher")
        }

}

object Test{

        def main(args: Array[String]): Unit = {

                val teacher: Teacher8 = new Teacher8()
                println(teacher.name)
                teacher.hello()

                val teacher2: Person8 = new Teacher8()
                println(teacher2.name)
                teacher2.hello()

        }

}



