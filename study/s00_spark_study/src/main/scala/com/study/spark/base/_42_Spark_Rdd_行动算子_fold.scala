package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    fold

        函数签名
            def fold(zeroValue: T)(op: (T, T) => T): T

        函数说明
            折叠操作(即分区内的操作和分区间的操作相同)，aggregate 的简化版操作

 */
object _42_Spark_Rdd_Fold {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("fold")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(2, 5, 3, 1, 2, 3, 4),8)

        //分区初始值为0
        val aggRdd1: Int = rdd.aggregate(0)(_ + _, _ + _)

        //初始值为10
        val aggRdd2: Int = rdd.aggregate(10)(_ + _, _ + _)

        println(aggRdd1)
        println(aggRdd2)
        sc.stop()

    }
}
