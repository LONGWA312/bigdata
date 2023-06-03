package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    reduce

        函数签名
            def reduce(f: (T, T) => T): T

        函数说明
            聚集 RDD 中的所有元素，先聚合分区内数据，再聚合分区间数据


 */
object _35_Spark_Rdd_Reduce {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("reduce")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        /*
            两两进行聚合运算
         */
        val reduceRusult: Int = rdd.reduce(_ + _)

        println(reduceRusult)

        sc.stop()

    }
}
