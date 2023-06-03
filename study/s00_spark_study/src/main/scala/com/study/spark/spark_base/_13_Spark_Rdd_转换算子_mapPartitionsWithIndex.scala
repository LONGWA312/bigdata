package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * mapPartitionsWithIndex
 *
 * 函数签名：
 *      def mapPartitionsWithIndex[U: ClassTag](
 *          f: (Int, Iterator[T]) => Iterator[U],
 *          preservesPartitioning: Boolean = false): RDD[U]
 *
 * 函数说明
 *      将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处理，哪怕是过滤数据，在处理时同时可以获取当前分区索引
 *
 *
 */
object _13_Spark_Rdd_mapPartitionsWithIndex {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartition")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 3)


        val newRdd = rdd.mapPartitionsWithIndex(
            /*
                index： 分区号
                iter：迭代器
             */
            (index, iter) => {
                iter.map(
                    num => {
                        (index, num)
                    }
                )
            }
        )

        newRdd.collect().foreach(println)

        sc.stop()

    }
}


/*
    获取第二个分区的数据
 */
object Spark_Rdd_MapPartitionsWithIndex {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartition")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 3)

        val newRdd = rdd.mapPartitionsWithIndex(
            (index, iter) => {
                if (index == 1) {
                    iter
                } else {
                    /*
                        返回一个空的迭代器
                     */
                    Nil.iterator
                }
            }
        )

        newRdd.collect().foreach(println)

        sc.stop()
    }
}