package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    intersection

        函数签名：
            def intersection(other: RDD[T]): RDD[T]

        函数说明
            对源 RDD 和参数 RDD 求交集后返回一个新的 RDD



    union: 对源 RDD 和参数 RDD 求并集后返回一个新的 RDD。求并集

    subtract: 以一个 RDD 元素为主，去除两个 RDD 中重复元素，将其他元素保留下来。求差集

    zip: 拉链操作，将两个 RDD 中的元素，以键值对的形式进行合并。其中，键值对中的Key为第1个RDD中的元素，Value 为第 2 个 RDD 中的相同位置的元素


        


 */
object _21_Spark_Rdd_Intersection {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SortBy")
        val sc = new SparkContext(sparkConf)

        /*
             交集，并集和差集要求两个数据源数据类型保持一致
             拉链操作两个数据源的类型可以不一致
         */

        val rdd1 = sc.makeRDD(List(1,2,3,4))
        val rdd2 = sc.makeRDD(List(3,4,5,6))
        val rdd7 = sc.makeRDD(List("3","4","5","6"))

        /*
            求交集
         */
        val rdd3: RDD[Int] = rdd1.intersection(rdd2)
        println("rdd3 = " + rdd3.collect().mkString(","))

        /*
            差集
         */
        val rdd4: RDD[Int] = rdd1.subtract(rdd2)
        println("rdd4 = " + rdd4.collect().mkString(","))


        /*
            并集
         */
        val rdd5: RDD[Int] = rdd1.union(rdd2)
        println("rdd5 = " + rdd5.collect().mkString(","))


        /*
            拉链
                Can't zip RDDs with unequal numbers of partitions: List(2, 4)
                两个数据源要求分区数量要保持一致
                Can only zip RDDs with same number of elements in each partition
                两个数据源要求分区中数据数量保持一致
         */
        val rdd6: RDD[(Int, Int)] = rdd1.zip(rdd2)
        println("rdd6 = " + rdd6.collect().mkString(","))

        /*
            不同数据类型的rdd之间的拉链操作
         */
        val rdd8: RDD[(Int, String)] = rdd1.zip(rdd7)
        println("rdd8 = " + rdd8.collect().mkString(","))

        sc.stop()

    }

}








