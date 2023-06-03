package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    countByKey

        函数签名
            def countByKey(): Map[K, Long]

        函数说明
            折叠操作(即分区内的操作和分区减的操作相同)，aggregate 的简化版操作

 */
object _43_Spark_Rdd_CountByKey{


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("countByKey")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 5), ("a", 95), ("b", 98)))

        val countRdd: collection.Map[String, Long] = rdd.countByKey()

        countRdd.foreach(println)
        sc.stop()

    }
}
