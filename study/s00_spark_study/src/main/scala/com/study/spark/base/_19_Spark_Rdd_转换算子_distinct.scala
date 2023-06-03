package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    distinct

    函数签名：
        def distinct()(implicit ord: Ordering[T] = null): RDD[T]
        def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T]

    函数说明
        将数据集中重复的数据去重


 */
object _19_Spark_Rdd_Distinct {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("distinct")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 1, 2), 1)

        val distinctRdd: RDD[Int] = rdd.distinct()

        val distinctRdd2: RDD[Int] = rdd.distinct(2)

        distinctRdd.collect().foreach(println)
        distinctRdd2.collect().foreach(println)

        sc.stop()

    }

}







