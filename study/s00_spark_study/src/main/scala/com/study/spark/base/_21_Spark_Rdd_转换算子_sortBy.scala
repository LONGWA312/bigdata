package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    sortBy

    函数签名：
        def sortBy[K](
            f: (T) => K,
            ascending: Boolean = true,
            numPartitions: Int = this.partitions.length)
            (implicit ord: Ordering[K], ctag: ClassTag[K]): RDD[T]


    函数说明
        该操作用于排序数据。在排序之前，可以将数据通过 f 函数进行处理，之后按照 f 函数处理的结果进行排序，默认为升序排列。
        排序后新产生的 RDD 的分区数与原 RDD 的分区数一致。中间存在 shuffle 的过程





 */
object _21_Spark_Rdd_SortBy {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SortBy")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(11, 2, 13, 4, 15, 6, 17, 8, 9, 10), 2)

        val newRdd: RDD[Int] = rdd.sortBy(num => num)
        println(newRdd.collect().mkString(","))


        val newRdd2: RDD[Int] = rdd.sortBy(num => num, false)
        println(newRdd2.collect().mkString(","))


        val rdd2 = sc.makeRDD(List(("1", 1), ("11", 2), ("2", 3)), 2)
        /*
             sortBy方法可以根据指定的规则对数据源中的数据进行排序，默认为升序，第二个参数可以改变排序的方式
             sortBy默认情况下，不会改变分区。但是中间存在shuffle操作
         */
        val newRdd3 = rdd2.sortBy(t=>t._1.toInt, false)
        println(newRdd3.collect().mkString(","))

        sc.stop()

    }

}








