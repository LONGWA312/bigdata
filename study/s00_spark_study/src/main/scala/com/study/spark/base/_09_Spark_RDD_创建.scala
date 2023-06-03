package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Spark 计算框架为了能够进行高并发和高吞吐的数据处理，封装了三大数据结构，用于处理不同的应用场景。三大数据结构分别是：
 * ➢ RDD : 弹性分布式数据集
 * ➢ 累加器：分布式共享只写变量
 * ➢ 广播变量：分布式共享只读变量
 *
 * RDD
 **
 *RDD（Resilient Distributed Dataset）叫做弹性分布式数据集，是 Spark 中最基本的数据处理模型。
 *代码中是一个抽象类，它代表一个弹性的、不可变、可分区、里面的元素可并行计算的集合。
 *➢ 弹性
 *⚫ 存储的弹性：内存与磁盘的自动切换；
 *⚫ 容错的弹性：数据丢失可以自动恢复；
 *⚫ 计算的弹性：计算出错重试机制；
 *⚫ 分片的弹性：可根据需要重新分片。
 *➢ 分布式：数据存储在大数据集群不同节点上
 *➢ 数据集：RDD 封装了计算逻辑，并不保存数据
 *➢ 数据抽象：RDD 是一个抽象类，需要子类具体实现
 *➢ 不可变：RDD 封装了计算逻辑，是不可以改变的，想要改变，只能产生新的 RDD，在新的 RDD 里面封装计算逻辑
 *➢ 可分区、并行计算
 *
 *
 *
 */
object _09_Spark_RDD {


    def main(args: Array[String]): Unit = {


        val sparkconf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("_09_Spark_RDD")

        val sc: SparkContext = new SparkContext(sparkconf)

        /**
         * 在 Spark 中创建 RDD 的创建方式可以分为四种：
         */
        /*
            1) 从集合创建RDD
            从集合中创建 RDD，Spark 主要提供了两个方法：parallelize 和 makeRDD
            从底层代码实现来讲，makeRDD 方法其实就是 parallelize 方法
         */
        val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4))
        val rdd2: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        rdd1.collect().foreach(println)
        rdd2.collect().foreach(println)


        /*
            2) 从外部存储（文件）创建 RDD
                由外部存储系统的数据集创建 RDD 包括：
                    本地的文件系统，
                    所有 Hadoop 支持的数据集，比如 HDFS、HBase 等。
         */
        val rdd3: RDD[String] = sc.textFile("s00_spark_study/data/input/01_word.txt")

        rdd3.foreach(println)


        /*
            3) 从其他 RDD 创建
                主要是通过一个 RDD 运算完后，再产生新的 RDD。
         */
        val rdd4: RDD[Int] = rdd2.map(_ + 1)
        rdd4.foreach(println)


        /*
            4) 直接创建 RDD（new）
                使用 new 的方式直接构造 RDD，一般由 Spark 框架自身使用。
         */


        sc.stop()

    }
}
































