package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext, rdd}


/**
 * mapPartitions
 *
 * 函数说明
 *      将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处理，哪怕是过滤数据
 *
 * map 和 mapPartitions的区别
 *      数据处理角度
 *          Map 算子是分区内一个数据一个数据的执行，类似于串行操作。
 *          mapPartitions 算子是以分区为单位进行批处理操作。
 *
 *      功能角度：
 *          Map 算子主要目的将数据源中的数据进行转换和改变。但是不会减少或增多数据。
 *          MapPartitions 算子需要传递一个迭代器，返回一个迭代器，没有要求的元素的个数保持不变，所以可以增加或减少数据
 *
 *      性能的角度：
 *          Map 算子因为类似于串行操作，所以性能比较低，
 *          mapPartitions 算子类似于批处理，所以性能较高。
 *
 *          但是 mapPartitions 算子会长时间占用内存，那么这样会导致内存可能不够用，出现内存溢出的错误。所以在内存有限的情况下，不推荐使用。使用 map 操作。
 *
 *
 */
object _12_Spark_Rdd_mapPartitions {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartition")

        val sc: SparkContext = new SparkContext(sparkConf)

        val dataRdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5))


        /*
            较少数据
         */
        val dataRdd1: RDD[Int] = dataRdd.mapPartitions(
            data => {
                data.filter(_%2 == 0)
            }
        )


        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        /*
            map是RDD的方法， 天生支持分布式计算
         */
        val rdd1 = rdd.map(
            num => {
                num * 2
            }
        )


        /*
            mapPartitions方法主要因为map方法效率较低， 所以为了提高效率
            将一个分区的数据全部加载到内存中直接做处理，类似与批处理，所以效率搞

            但是内存是有限度的，且mapPartitions会以分区为单位进行数据的计算
            如果分区内的数据没有完全计算完毕，那么之前计算完毕的数据不会被释放
            如果整个分区的数据全部计算完毕，内存才会释放
            mapPartitions可能会到导致内存溢出
         */
        val rdd2: RDD[Int] = rdd.mapPartitions(
            itor => {
                /*
                    这个map方法是scala中集合的方法
                 */
                itor.map(_ * 2)
            }
        )

        rdd2.collect().foreach(println)


    }
}


object Spark_Rdd_mapPartitions{

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartition")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"), 2)


        /*
            增加数据
         */
        val newRdd: RDD[String] = rdd.mapPartitions(
            iter => {
                iter.flatMap(_.split(" "))
            }
        )

        newRdd.collect().foreach(println)


        sc.stop()
    }

}



/*
    获取每个数据分区的最大值
 */
object Spark_Rdd_mapPartition2{

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("mapPartition")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5), 2)


        /*
            函数名称： 输入， 输出
         */
        val newRdd = rdd.mapPartitions(
            iter => {
                List(iter.max).iterator
            }
        )

        newRdd.collect().foreach(println)


        sc.stop()
    }

}