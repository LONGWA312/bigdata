package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    take

        函数签名
            def take(num: Int): Array[T]

        函数说明
            返回一个由 RDD 排序后 的前 n 个元素组成的数组

 */
object _40_Spark_Rdd_TakeOrdered {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("takeOrdered")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(2, 5, 3, 1, 2, 3, 4),2)

        val take: Array[Int] = rdd.takeOrdered(3)

        println(take.mkString(","))

        sc.stop()

    }
}
