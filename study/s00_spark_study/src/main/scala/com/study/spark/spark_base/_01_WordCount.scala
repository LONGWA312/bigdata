package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object _01_WordCount {


    def main(args: Array[String]): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        val sc: SparkContext = new SparkContext(sparkConf)

        val fileRdd: RDD[String] = sc.textFile("spark_study\\src\\main\\input\\01_word.txt")

        val wordRDD: RDD[String] = fileRdd.flatMap(_.split(" "))

        val wordToOneRdd: RDD[(String, Int)] = wordRDD.map((_, 1))

        val wordToCountRdd: RDD[(String, Int)] = wordToOneRdd.reduceByKey(_ + _)

        val wordCount: Array[(String, Int)] = wordToCountRdd.collect()

        wordCount.foreach(println)

        sc.stop()



    }
}
