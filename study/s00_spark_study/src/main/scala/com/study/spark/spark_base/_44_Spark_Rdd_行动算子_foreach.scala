package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    foreach

        函数签名
            def foreach(f: T => Unit): Unit = withScope {
                 val cleanF = sc.clean(f)
                 sc.runJob(this, (iter: Iterator[T]) => iter.foreach(cleanF))
            }


        函数说明
            分布式遍历 RDD 中的每一个元素，调用指定函数


 */
object _44_Spark_Rdd_Foreach{


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Foreach")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 5), ("a", 95), ("b", 98)))

        val countRdd: collection.Map[String, Long] = rdd.countByKey()

        countRdd.foreach(println)

        rdd.foreach(println)
        sc.stop()

    }
}
