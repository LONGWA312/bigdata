package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    count

        函数签名
            def count(): Long

        函数说明
            返回 RDD 中元素的个数

 */
object _37_Spark_Rdd_Count {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("collect")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

        val count: Long = rdd.count()

        println(count)

        sc.stop()

    }
}
