package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    combineByKey

        函数签名：
            def combineByKey[C](
                 createCombiner: V => C,
                 mergeValue: (C, V) => C,
                 mergeCombiners: (C, C) => C): RDD[(K, C)]

        函数说明
            最通用的对 key-value 型 rdd 进行聚集操作的聚集函数（aggregation function）。
            类似于aggregate()，combineByKey()允许用户返回值的类型与输入不一致。


        reduceByKey、foldByKey、aggregateByKey、combineByKey 的区别？

            reduceByKey: 相同 key 的第一个数据不进行任何计算，分区内和分区间计算规则相同
            foldByKey: 相同 key 的第一个数据和初始值进行分区内计算，分区内和分区间计算规则相同
            aggregateByKey：相同 key 的第一个数据和初始值进行分区内计算，分区内和分区间计算规则可以不相同
            combineByKey:当计算时，发现数据结构不满足要求时，可以让第一个数据转换结构。分区内和分区间计算规则不相同。



 */
object _28_Spark_Rdd_combineByKey {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("combineByKey")
        val sc = new SparkContext(sparkConf)

        val list: List[(String, Int)] =  List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98))

        val rdd: RDD[(String, Int)] = sc.makeRDD(list,2)

        val combineRdd: RDD[(String, (Int, Int))] = rdd.combineByKey(
            (_, 1),
            (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
            (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc2._2 + acc2._2)
        )

        combineRdd.collect().foreach(println)

        sc.stop()

    }

}








