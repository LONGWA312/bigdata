package com.study.scala.base

/**
 * 模式守卫
    如果想要表达匹配某个范围的数据，就需要在模式匹配中增加条件守卫。
 */
object TestMatchGuard {

    def main(args: Array[String]): Unit = {

        def abs(x: Int) = x match {
            case i: Int if i >= 0 => i
            case j: Int if j < 0 => -j
            case _ => "type illegal"
        }

        def abs2(x: Int) = {
            x match {
                case i: Int if i >= 0 => i
                case j: Int if j < 0 => -j
                case _ => "type illegal"
            }
        }
        println(abs(-5))
    }

}
