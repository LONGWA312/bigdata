package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    collect

        函数签名
            def collect(): Array[T]

        函数说明
            在驱动程序中，以数组 Array 的形式返回数据集的所有元素
            （在Driver中将所有的数据加载Array中）


 */
object _36_Spark_Rdd_Collect {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("collect")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

        val ints: Array[Int] = rdd.collect()

        println(ints.mkString(","))

        /*val lines = sc.textFile("")
        lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)*/

        sc.stop()

    }
}
