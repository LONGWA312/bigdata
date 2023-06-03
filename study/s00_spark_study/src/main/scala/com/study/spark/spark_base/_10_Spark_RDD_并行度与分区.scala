package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Spark并行度与分区
        默认情况下，Spark 可以将一个作业切分多个任务后，发送给 Executor 节点并行计算，而能够并行计算的任务数量我们称之为并行度。
        这个数量可以在构建 RDD 时指定。记住，这里的并行执行的任务数量，并不是指的切分任务的数量，不要混淆了。

 * 读取内存数据时，数据可以按照并行度的设定进行数据的分区操作
 * 读取文件数据时，数据是按照 Hadoop 文件读取的规则进行切片分区，而切片规则和数据读取的规则有些差异
 *
 */
object _10_Spark_RDD {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("_10_Spark_RDD")

        val sc = new SparkContext(sparkConf)

        val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 4)
        val rdd2: RDD[String] = sc.textFile("s00_spark_study/data/input/01_word.txt", 2)

        rdd1.foreach(println)
        rdd2.foreach(println)

        rdd1.collect().foreach(println)
        rdd2.collect().foreach(println)

    }

    /*
        读取内存数据时，数据可以按照并行度的设定进行数据的分区操作
     */
    def position(length: Long, numSlices: Int): Iterator[(Int, Int)] = {
        (0 until numSlices).iterator.map( i => {
            val start = ((i * length) / numSlices).toInt
            val end = (((i + 1) /length)).toInt
            (start, end)
        })
    }
}
