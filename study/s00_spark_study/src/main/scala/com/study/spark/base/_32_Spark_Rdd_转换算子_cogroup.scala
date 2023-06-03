package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    cogroup

        函数签名：
            def cogroup[W](other: RDD[(K, W)]): RDD[(K, (Iterable[V], Iterable[W]))]

        函数说明
            在类型为(K,V)和(K,W)的 RDD 上调用，返回一个(K,(Iterable<V>,Iterable<W>))类型的 RDD

 */
object _32_Spark_Rdd_cogroup {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("join")
        val sc = new SparkContext(sparkConf)

        val rdd1: RDD[(String, Int)] = sc.makeRDD(
            Array(("a", 1), ("b", 1), ("c", 1), ("c", 2), ("c", 3))
        )
        val rdd4: RDD[(String, String)] = sc.makeRDD(
            Array(("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"), ("c", "F"))
        )

        val cogroupRdd: RDD[(String, (Iterable[Int], Iterable[String]))] = rdd1.cogroup(rdd4)
        cogroupRdd.collect().foreach(println)

        sc.stop()
    }

}








