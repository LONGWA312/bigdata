package com.study.spark.base

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    filter

    函数签名：
        def filter(f: T => Boolean): RDD[T]

    函数说明
        将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃。
        当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡，生产环境下，可能会出现数据倾斜。

 */
object _17_Spark_Rdd_Filter {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("filter")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 1)

        val filterRdd: RDD[Int] = rdd.filter(_ % 2 == 0)

        filterRdd.collect().foreach(println)

        sc.stop()

    }

}


/*
    从服务器日志数据 apache.log 中获取 2015 年 5 月 17 日的请求路径
 */
object Spark_Rdd_Filter_Test {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("filter_test")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("s00_spark_study/data/input/apache.log")

        val filterRdd = rdd.filter(
            line => {
                line.contains("17/05/2015:")
            }
        )
        filterRdd.collect().foreach(println)

        val requestPathRdd: RDD[String] = filterRdd.map(
                    line => {
                line.split(" ")(6)
            }
        )
        requestPathRdd.collect().foreach(println)

        sc.stop()

    }
}






