package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/*

Rdd文件的读取和保存

    Spark的数据读取及数据保存可以从两个维度来做区分： 文件格式以及文件系统
    文件格式分为: text文件，csv文件，sequence文件以及Object文件
    文件系统分为: 本地文件系统，HDFS, Hbase以及数据库


 */



object _049_Spark_Rdd_FileReadAndWrite {


    def main(args: Array[String]): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("io")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List("a", "b", "c"))

        rdd.saveAsTextFile("")
        rdd.saveAsObjectFile("")

        /*
            保存sequenceFile需要kv结构的数据
         */
        rdd.map((_, 1)).saveAsSequenceFile("")

    }


    def main1(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("io")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd = sc.textFile("")
        println(rdd.collect().mkString(","))

        val rdd1 = sc.objectFile[String]("")
        println(rdd1.collect().mkString(","))


        val rdd2 = sc.sequenceFile[String, Int]("")
        println(rdd2.collect().mkString(","))


    }


}
