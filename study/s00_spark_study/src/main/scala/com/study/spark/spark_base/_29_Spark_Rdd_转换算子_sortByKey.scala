package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    sortByKey

        函数签名：
            def sortByKey(ascending: Boolean = true, numPartitions: Int = self.partitions.length): RDD[(K, V)]

        函数说明
            在一个(K,V)的 RDD 上调用，K 必须实现 Ordered 接口(特质)，返回一个按照 key 进行排序的



 */
object _29_Spark_Rdd_sortByKey {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sortByKey")
        val sc = new SparkContext(sparkConf)

        val list: List[(String, Int)] =  List(("a", 1), ("b", 2), ("c", 3), ("d", 5), ("a", 95), ("b", 98))

        val rdd: RDD[(String, Int)] = sc.makeRDD(list,2)

        /*
            参数true指定升序，false指定为降序
         */
        val ascRdd: RDD[(String, Int)] = rdd.sortByKey(true)
        ascRdd.collect().foreach(println)

        val descRdd: RDD[(String, Int)] = rdd.sortByKey(false)
        descRdd.collect().foreach(println)

        sc.stop()

    }

}








