package com.study.scala.base

/*
字符串输出

1.基本语法
    （1）字符串，通过+号连接
    （2）printf 用法：字符串，通过%传值。
    （3）字符串模板（插值字符串）：通过$获取变量值


 */
object _03_StringPrint {

    def main(args: Array[String]): Unit = {


        var name : String = "scala"
        var age : Int = 18


        /*
        （1）字符串，通过+号连接
         */
        println(name + " " + age)


        /*
        （2）printf 用法：字符串，通过%传值
         */
        printf("name=%s, age=%d\n", name, age)



        /*
        （3）字符串模板（插值字符串）：通过$获取变量值

            * 多行字符串，在 Scala中，利用三个双引号包围多行字符串就可以实现。
            * 输入的内容，带有空格、\t 之类，导致每一行的开始位置不能整洁对齐。
            * 应用 scala 的 stripMargin 方法，在 scala 中 stripMargin 默认
            * 是“|”作为连接符，//在多行换行的行头前面加一个“|”符号即可。
         */
        val s1 =
            s"""
               |select
               |    name,
               |    age
               |from
               |    user
               |where
               |    name='zhangsan';
               |""".stripMargin

        println("s1 = " + s1)


        /*
        （3）字符串模板（插值字符串）：通过$获取变量值
            如果需要对变量进行运算，那么可以加${}
         */
        val s2 =
            s"""
               |select
               |    name,
               |    age
               |from
               |    user
               |where
               |    name=$name and age=${age+2};
               |""".stripMargin
        println(s2)


        /*
          符串模板（插值字符串）：通过$获取变量值
         */
        val s3 = s"name=$name"
        println(s3)

    }

}
