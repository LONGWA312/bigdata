package com.study.scala.base

/*

变量和常量

1.常量

    在执行过程中其值不会备改变的变量

2. java的常量和变量定义语法

    常量： final 常量类型 常量名称 = 初始值 final int b = 20
    变量： 变量类型 变量名称 = 初始值 int a = 10

3. scala的常量和变量基本语法

    var 变量名 [: 变量类型] = 初始值 var i:Int = 10
    val 常量名 [: 常量类型] = 初始值 val j:Int = 20
    注意：能用常量的地方不用变量


4. 注意：

    （1）声明变量时，类型可以省略，编译器自动推导，即类型推导
    （2）类型确定后，就不能修改，说明 Scala 是强数据类型语言。
    （3）变量声明时，必须要有初始值
    （4）在声明/定义一个变量时，可以使用 var 或者 val 来修饰，var 修饰的变量可改变， val 修饰的变量不可改。

 */

object _02_Variable_and_Constant {


    def main(args: Array[String]): Unit = {

        TestVariable.test_var()

    }
}
/**
 * 变量测试
 */
object TestVariable{


    def test_var(): Unit ={

        //定义变量
        var age = 18
        var name: String = "Tom"

        println("age="+age)
        println("name="+name)

        //修改变量的值
        age = 20
        name = "Lisa"

        println("age="+age)
        println("name="+name)

    }


}


/**
 * 常量测试
 */
object TestConstant{

    def test_con(): Unit ={

        //定义常量
        val age = 18
        val name: String = "Tom"

        println("age="+age)
        println("name="+name)


        //修改常量的值。
        //age = 20          // 错误
        //name = "Lisa"     // 错误

        println("age="+age)
        println("name="+name)

    }

}


/*

    var 修饰的对象引用可以改变，val 修饰的对象则不可改变，但对象的状态（值）却是可以改变的。（比如：自定义对象、数组、集合等等）

 */

class Person {

    var name : String = "Scala"

}

object TestPerson {

    def main(args: Array[String]): Unit = {

        println("test person: ")

        // p1 是 var 修饰的，p1 的属性可以变，而且 p1 本身也可以变
        var p1 = new Person()
        println(p1.name)

        p1.name = "Test Scala"
        println(p1.name)

        p1 = null
        println(p1)


        // p2 是 val 修饰的，那么 p2 本身就不可变（即 p2 的内存地址不能变），但是，p2 的属性是可以变，因为属性并没有用 val 修饰。
        val p2 = new Person()
        println(p2.name)

        p2.name = "Test Scala"
        println(p2.name)

        //p2 = null     // 错误
        //println(p2)


    }
}



/*

    var _: String = "hello" // ok 单独一个下划线不可以作为标识符，因为_被认为是一个方法println(_)
 */




