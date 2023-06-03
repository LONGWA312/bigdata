package com.study.scala.base

import scala.collection.immutable
import scala.collection.parallel.immutable.ParSeq


/**
 * 并行集合
 **
 * Scala 为了充分使用多核 CPU，提供了并行集合（有别于前面的串行集合），用于多核环境的并行计算。
 */
object TestPar {

    def main(args: Array[String]): Unit = {

        val result1: immutable.IndexedSeq[String] = (0 to 100).map { case _ => Thread.currentThread().getName }

        val result2: ParSeq[String] = (0 to 100).par.map { case _ => Thread.currentThread().getName }

        println(result1)
        println(result2)

    }

}
