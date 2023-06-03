package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    partitionBy

        函数签名：
            def partitionBy(partitioner: Partitioner): RDD[(K, V)]

        函数说明
            将数据按照指定 Partitioner 重新进行分区。Spark 默认的分区器是 HashPartitioner


        目前改变分区的操作有三个，区别主要如下：
            coalasec: 缩减分区
            repartition: 扩大分区
            partitionBy: 主要用于改变数据所在的分区，需要通过特定的分区规则对数据进行分区，所以需要分区器

        现成的分区器有：
            抽象类Partitioner,其默认实现的分区器有：
                HashPartitioner: hash分区器
                RangePartitioner: 随机分区器
                PythonPartitioner:

        Spark中默认采用的分区器是HashPartitioner,使用key的hash值，以及分区数量进行取余计算，获取数据所在的分区

 */
object _23_Spark_Rdd_PartitionBy {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SortBy")
        val sc = new SparkContext(sparkConf)

        /*
            partitionBy根据指定的分区规则对数据进行重分区
         */
        val rdd = sc.makeRDD(List(1,2,3,4,5,6))

        val mapRdd: RDD[(Int, Int)] = rdd.map((_, 1))

        val newRdd: RDD[(Int, Int)] = mapRdd.partitionBy(new HashPartitioner(2))
        val newRdd2: RDD[(Int, Int)] = newRdd.partitionBy(new HashPartitioner(2))

        println(newRdd.collect().mkString(","))
        println(newRdd2.collect().mkString(","))

        sc.stop()

    }

}








