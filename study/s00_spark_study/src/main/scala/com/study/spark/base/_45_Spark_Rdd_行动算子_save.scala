package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    save相关的算子

        函数签名
            def saveAsTextFile(path: String): Unit
            def saveAsObjectFile(path: String): Unit
            def saveAsSequenceFile(path: String,codec: Option[Class[_ <: CompressionCodec]] = None): Unit

        函数说明
            将数据保存到不同格式的文件中


 */
object _44_Spark_Rdd_Save{


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Save")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 5), ("a", 95), ("b", 98)))

        /*
            保存成Text文件
         */
        rdd.saveAsTextFile("")

        /*
            将数据保存到不同格式的文件中
         */
        rdd.saveAsObjectFile("")


        /*
            保存成 Sequencefile 文件
         */
        rdd.saveAsSequenceFile("")


        /*
            rdd.foreach: 分布式循环遍历，顺序是无法保证
            rdd.collect.foreach: collect会将数据拉取到Driver端内存中
         */
        rdd.foreach(println)
        rdd.collect().foreach(println)


        sc.stop()

    }
}
