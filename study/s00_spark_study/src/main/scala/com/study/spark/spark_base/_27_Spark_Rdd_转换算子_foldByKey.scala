package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    foldByKey

        函数签名：
            def foldByKey(zeroValue: V)(func: (V, V) => V): RDD[(K, V)]

        函数说明
            当分区内计算规则和分区间计算规则相同时，aggregateByKey 就可以简化为 foldByKey


 */
object _27_Spark_Rdd_foldByKey {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("foldByKey")
        val sc = new SparkContext(sparkConf)



        val rdd: RDD[(String, Int)] = sc.makeRDD(
            List(
                ("a", 1), ("a", 2), ("b", 3), ("b", 4),
                ("a", 4), ("a", 5), ("b", 6), ("b", 7)
            ),
            2
        )

        /*
            分区内求最大值，分区间求最大值
         */
        val foldRdd: RDD[(String, Int)] = rdd.foldByKey(0)(
            (x, y) => {
                math.max(x, y)
            }
        )
        //简化
        val foldRdd2: RDD[(String, Int)] = rdd.foldByKey(0)((x, y) => math.max(x, y))
        foldRdd.collect().foreach(println)



        /*
            分区内求和，分区间求和
         */
        val foldRdd1: RDD[(String, Int)] = rdd.foldByKey(0)(
            (x, y) => {
                x + y
            }
        )
        foldRdd1.collect().foreach(println)

        /*
            省略写法
         */
        val foldRdd3 = rdd.foldByKey(0)(_ + _)
        foldRdd3.collect().foreach(println)

        sc.stop()

    }

}








