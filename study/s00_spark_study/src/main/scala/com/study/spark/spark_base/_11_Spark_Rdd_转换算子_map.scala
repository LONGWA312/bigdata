package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * 转换算子
 * RDD 根据数据处理方式的不同将算子整体上分为 Value 类型、双 Value 类型和 Key-Value
 *
 * 单值：
 * 双值：
 * 键值：
 */


/*
    Value类型 - map

    1. 函数签名
        def map[U: ClassTag](f: T => U): RDD[U]

    2. 函数说明
        将处理的数据逐条进行映射转换，这里的转换可以是类型的转换，也可以是值的转换。

    Rdd转换算子默认情况下，新的RDD分区数量和旧的RDD的分区数量保持一致
    每个分区的数据处理完成后还在哪个分区
    分区间数据是无序的
    分区内的数据是有序的
    分区内的数据是迭代操作（串行）：即每条数据在多个map连续操作后在操作下一条



 */
object TestMap{

    def main(args: Array[String]): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("testMap")

        val sc = new SparkContext(sparkConf)

        val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        /*
            值的转换
         */
        val rdd2: RDD[Int] = rdd1.map(m => {
            m * 2
        })
        rdd2.foreach(println)

        /*
            类型的转换
         */
        val rdd3: RDD[String] = rdd1.map(m => {
            "" + m
        })
        rdd3.foreach(println)


        /**
         *  转换后分区数量不变
         */


        sc.stop()

    }


    def main(): Unit = {



    }
}



object TestMap2{

    /**
        案例：从服务器日志数据 apache.log 中获取用户请求 URL 资源路径
     */
    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("map_test_2")
        val sc: SparkContext = new SparkContext(sparkConf)

        val textRdd = sc.textFile("s00_spark_study/data/input/apache.log")

        val urlRdd= textRdd.map(m => m.split(" ")).map(m => {
            val length: Int = m.length
            m(m.length-1)
        })

        urlRdd.foreach(println)
        sc.stop()
    }
}
