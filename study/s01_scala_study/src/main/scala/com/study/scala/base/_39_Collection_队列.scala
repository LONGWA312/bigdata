package com.study.scala.base

import scala.collection.mutable


/**
 * 队列
 * Scala 也提供了队列（Queue）的数据结构，队列的特点就是先进先出。进队和出队的方法分别为 enqueue 和 dequeue。
 */
object TestQueue {

    def main(args: Array[String]): Unit = {

        val que: mutable.Queue[String] = new mutable.Queue[String]()

        que.enqueue("a", "b", "c")

        println(que.dequeue())
        println(que.dequeue())
        println(que.dequeue())

    }
}
