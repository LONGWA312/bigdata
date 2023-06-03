package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    repartition

    函数签名：
        def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T]

    函数说明
        该操作内部其实执行的是 coalesce 操作，参数 shuffle 的默认值为 true。
        无论是将分区数多的RDD 转换为分区数少的 RDD，还是将分区数少的 RDD 转换为分区数多的 RDD，repartition操作都可以完成，
        因为无论如何都会经 shuffle 过程。


    coalesce 和 repartition有什么区别？

    coalesce和repartition都能用来调整分区数量，但是repatition的底层调用的是coalesce
    coalesce的语法: coalesce(num,shuffle=False) 默认不启动shuffle
    repartition的语法: repartition(num) 默认启动shuffle
    repartition常用于增加分区,coalesce常用于减小分区，关键就在于shuffle是否启动
    重新分区的根本是通过hash取模后再分区,因此必须通过shuffle

    分区数据重新分区时会出现1个分区数据分配到其他多个分区的情况,也就形成了「宽依赖」
    减少分区的本质是将一个分区完整归类到另一个分区，属于一对一的情况，也就形成了窄依赖




 */
object _21_Spark_Rdd_Repartitions {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("repartition")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)


        val newRdd: RDD[Int] = rdd.repartition(4)

        println(newRdd.collect().mkString(","))

        sc.stop()

    }

}








