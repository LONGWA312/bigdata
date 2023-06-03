package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    coalesce

    函数签名：
        def coalesce(numPartitions: Int, shuffle: Boolean = false,
             partitionCoalescer: Option[PartitionCoalescer] = Option.empty)
             (implicit ord: Ordering[T] = null)

    函数说明
        根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率当 spark 程序中，
        存在过多的小任务的时候，可以通过 coalesce 方法，收缩合并分区，减少分区的个数，减小任务调度成本


 */
object _20_Spark_Rdd_Coalesce {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Coalesce")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 6)


        /*
             coalesce方法默认情况下不会将分区的数据打乱重新组合
             这种情况下的缩减分区可能会导致数据不均衡，出现数据倾斜
             如果想要让数据均衡，可以进行shuffle处理
         */
        val newRdd: RDD[Int] = rdd.coalesce(2)
        val newRdd2: RDD[Int] = rdd.coalesce(2, shuffle = true)

        println(newRdd.collect().mkString(","))
        println(newRdd2.collect().mkString(","))

        sc.stop()

    }

}


object _20_Spark_Rdd_Coalesce_Test {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Coalesce_test")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 2)


        /*
             coalesce算子可以扩大分区的，但是如果不进行shuffle操作，是没有意义，不起作用。
             所以如果想要实现扩大分区的效果，需要使用shuffle操作
             spark提供了一个简化的操作
             缩减分区：coalesce，如果想要数据均衡，可以采用shuffle
             扩大分区：repartition, 底层代码调用的就是coalesce，而且肯定采用shuffle
         */
        val newRdd: RDD[Int] = rdd.coalesce(3, shuffle = true)
        val newRdd2 = rdd.repartition(3)


        sc.stop()

    }

}








