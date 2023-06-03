package com.study.scala.base

/**
 * 模式匹配
 * 基本语法
    模式匹配语法中，采用 match 关键字声明，每个分支采用 case 关键字进行声明，
    当需要匹配时，会从第一个 case 分支开始，如果匹配成功，那么执行对应的逻辑代码，
    如果匹配不成功，继续执行下一个分支进行判断。如果所有 case 都不匹配，那么会执行 case _分支，类似于 Java 中 default 语句。

 * 说明
    （1）如果所有 case 都不匹配，那么会执行 case _ 分支，类似于 Java 中 default 语句，若此时没有 case _ 分支，那么会抛出 MatchError。
    （2）每个 case 中，不需要使用 break 语句，自动中断 case。
    （3）match case 语句可以匹配任何类型，而不只是字面量。
    （4）=> 后面的代码块，直到下一个 case 语句之前的代码是作为一个整体执行，可以使用{}括起来，也可以不括。


 */
object TestMatchCase {

    def main(args: Array[String]): Unit = {

        var a: Int = 10
        var b: Int = 20

        var operator: Char = 'd'

        var result = operator match {
            case '+' => a + b
            case '-' => a - b
            case '*' => a * b
            case '/' => a / b
            case _ => "illegal"
        }

        println(result)

    }
}
