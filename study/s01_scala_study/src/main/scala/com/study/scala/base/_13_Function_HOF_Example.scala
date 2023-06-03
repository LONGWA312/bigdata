package com.study.scala.base

import scala.collection.mutable.ArrayBuffer


/**
 *
 * 高阶函数案例
 *
 */
object _13_Function_HOF_Example {


    def main(args: Array[String]): Unit = {

        main()
    }


    def main(): Unit ={

        /*
            需求：模拟 Map 映射、Filter 过滤、Reduce 聚合
         */
        def map(arr: Array[Int], op: Int => Int) ={
            for (elem <- arr) yield op(elem)
        }

        // 将匿名函数elem => elem * elem作为参数传递
        val ints: Array[Int] = map(Array(1, 2, 3, 4, 5), elem => elem * elem)
        println(ints.mkString(","))



        /*
            filter 过滤。有参数，且参数再后面只使用一次，则参数省略且后面参数用_表示
         */
        def filter(array: Array[Int], op: Int => Boolean) ={
            var arr: ArrayBuffer[Int] = ArrayBuffer[Int]()
            for (elem <- array if op(elem)){
                arr.append(elem)
            }
            arr.toArray
        }

        val filted_arr: Array[Int] = filter(Array(1, 2, 3, 4, 5, 6), _ % 2 == 1)
        println(filted_arr.mkString(","))


        /*
             （3）reduce 聚合。有多个参数，且每个参数再后面只使用一次，则参数省略且后面参数用_表示，第 n 个_代表第 n 个参数
         */
        def reduce(arr: Array[Int], op: (Int, Int) => Int) = {
            var init: Int = arr(0)
            for (elem <- 1 to arr.length){
                init = op(init, elem)
            }
            init
        }

        val rs1 = reduce(Array(1, 2, 3, 4), (x, y) => x * y)
        val rs2 = reduce(Array(1, 2, 3, 4), _ * _)
        println(rs1)
        println(rs2)
    }
}
