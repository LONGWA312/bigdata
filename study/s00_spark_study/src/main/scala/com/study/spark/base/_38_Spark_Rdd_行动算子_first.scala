package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    first

        函数签名
            def first(): T

        函数说明
            返回 RDD 中的第一个元素

 */
object _38_Spark_Rdd_First {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("collect")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

        val first: Long = rdd.first()

        println(first)

        sc.stop()

    }
}
