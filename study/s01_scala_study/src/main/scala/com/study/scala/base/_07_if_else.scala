package com.study.scala.base

import scala.io.StdIn

/**
 **
流程控制
 *让程序有选择的的执行，分支控制有三种：单分支、双分支、多分支
 **
 基本语法：
    *if(条件表达式){
        *执行代码快
    *}
 *
 *
 */
object _07_if_else {

    /**
     * 单分支
     *      if(条件表达式){
     *          执行代码快
     *      }
     */
    def testIf(): Unit = {

        println("input age:")
        val age = StdIn.readShort()

        if (age > 18){
            println("")
        }
    }


    /**
     * 双分支
     *      if(条件表达式){
     *          代码块
     *      } else{
     *          代码块
     *      }
     */
    def testIfElse(): Unit = {

        println("input age:")
        val age = StdIn.readShort()

        if (age > 18){
            println("  ")
        } else {
            println("  ")
        }

    }

    /**
     * 多分支
     *
     *      if(条件表达式){
     *          print()
     *      } else if(条件表达式){
     *          print()
     *      } else {
     *          print()
     *      }
     */
    def testIfElses(): Unit = {

        println("input age:")
        val age = StdIn.readShort()

        if (age > 18){
            println("  ")
        } else if(age > 50) {
            println("  ")
        } else {
            println("  ")
        }
    }


    /**
     * if else分支在scala中其实是有返回值的
     */
    def testIfElses2(): Unit = {

        println("input age:")
        val age = StdIn.readShort()

        val res: String =  if (age > 18){
            "123"
        } else if(age > 50) {
            "456"
        } else {
            "789"
        }

        println(res)
    }


    /**
     * if else：Scala 中返回值类型不一致，取它们共同的祖先类型。
     */
    def testIfElses3(): Unit = {

        println("input age:")
        val age = StdIn.readShort()

        val res: Any =  if (age > 18){
            "123"
        } else if(age > 50) {
            "456"
        } else {
            789
        }

        println(res)

    }


    def main(args: Array[String]): Unit = {
        testIf()

        testIfElse()

        testIfElses()

        testIfElses2()

        testIfElses3()
    }

}
