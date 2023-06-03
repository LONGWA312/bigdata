package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    aggregate

        函数签名
            def aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U): U

        函数说明
            分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的数据聚合

 */
object _41_Spark_Rdd_Aggregate {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("aggregate")
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
