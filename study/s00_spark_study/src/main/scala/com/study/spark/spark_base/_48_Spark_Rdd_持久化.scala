package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/*

RDD持久化

    1. RDD Cache 缓存
        RDD 通过 Cache 或者 Persist 方法将前面的计算结果缓存，默认情况下会把数据以缓存在JVM的堆内存中。
        但是并不是这两个方法被调用时立即缓存，而是触发后面的 action 算子时，该 RDD 将会被缓存在计算节点的内存中，并供后面重用。

        rdd.cache():  数据缓存。直接缓存数据到jvm中
        rdd.persist(StorageLevel.MEMORY_AND_DISK_2):  可以更改数据存储级别


 */



object _048_Spark_Rdd_Cache {


    def main(args: Array[String]): Unit = {
        main()
//        main2()
    }

    /*
        默认情况下RDD对象可以重复使用，但是其中处理的数据无法重复使用
        如果多个行动算子执行，那么会从头执行数据的加载和处理过程
        这样的效率不高，为了解决这种重复使用的问题，spark提供了持久化操作
        spark中持久化分为不同的处理
        1. 缓存
        2. 磁盘文件
        3. 堆外内存OFF_HEAP: 为了和jvm管理的内存进行分区，从操作系统借来使用的内存称之为堆外内存

     */
    def main(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        val sc: SparkContext = new SparkContext(sparkConf)

        val fileRdd: RDD[String] = sc.textFile("s00_spark_study/data/input/1_word.txt")

        val wordRdd: RDD[String] = fileRdd.flatMap(line => {
            println("flatMap---------------------")
            line.split(" ")
        })

        val mapRdd: RDD[(String, Int)] = wordRdd.map(word => {
            println("map--------------------------")
            (word, 1)
        })

        val wordCountRdd: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
        val wordGroupRdd: RDD[(String, Iterable[Int])] = mapRdd.groupByKey()

        println(wordCountRdd.collect().mkString(","))
        println("***********************")
        println(wordGroupRdd.collect().mkString(","))

        sc.stop()

    }

    /*
        Cache操作

        cache和persist
        cache底层调用的就是persist，持久化级别设置为MEMORY_ONLY

        spark针对shuffle算子提供缓存操作

     */
    def main2(): Unit ={


        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        val sc: SparkContext = new SparkContext(sparkConf)

        val fileRdd: RDD[String] = sc.textFile("s00_spark_study/data/input/1_word.txt")

        val wordRdd: RDD[String] = fileRdd.flatMap(line => {
            println("flatMap---------------------")
            line.split(" ")
        })

        val mapRdd: RDD[(String, Int)] = wordRdd.map(word => {
            println("map--------------------------")
            (word, 1)
        })

        /*
            cache(): 缓存, 消耗更多的资源，容易出现故障
         */
//        mapRdd.cache()

        /*
            磁盘文件
            persist(StorageLevel.DISK_ONLY)
         */
        mapRdd.persist(StorageLevel.DISK_ONLY)

        val wordCountRdd: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
        val wordGroupRdd: RDD[(String, Iterable[Int])] = mapRdd.groupByKey()

        println(wordCountRdd.collect().mkString(","))
        println("***********************")
        println(wordGroupRdd.collect().mkString(","))

        sc.stop()
    }



    /*
        checkpoint

        数据如果存储在缓存中，数据不是很安全的，所以一旦出现问题，需要从头执行，那么效率太低了，
        所以spark提供了将临时数据处理结果保存到分布式系统中的功能
        检查点，需要指定数据存储的目录

        检查点为了保证数据的准确性，会独立创建一个作业执行，也就是前面所有的算子会重新走一遍，所以一般和cache联合使用
     */

    def main3(): Unit ={

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
        val sc: SparkContext = new SparkContext(sparkConf)

        /*
            设定检查点的保存路径
         */
        sc.setCheckpointDir("cp")


        val fileRdd: RDD[String] = sc.textFile("s00_spark_study/data/input/1_word.txt")

        val wordRdd: RDD[String] = fileRdd.flatMap(line => {
            println("flatMap---------------------")
            line.split(" ")
        })

        val mapRdd: RDD[(String, Int)] = wordRdd.map(word => {
            println("map--------------------------")
            (word, 1)
        })


        /*
            检查店操作会切断血缘关系，而持久化会在血缘关系增加依赖
            检查点会更安全一些，但是持久化会更快一些
            为什么检查点会切断血缘关系，因为等同于改变了数据源
         */
        mapRdd.cache()
        mapRdd.checkpoint()

        val wordCountRdd: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
        val wordGroupRdd: RDD[(String, Iterable[Int])] = mapRdd.groupByKey()

        println(wordCountRdd.collect().mkString(","))
        println("***********************")
        println(wordGroupRdd.collect().mkString(","))

        sc.stop()

    }
}
