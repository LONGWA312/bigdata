package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    reduceByKey

        函数签名：
            def reduceByKey(func: (V, V) => V): RDD[(K, V)]
            def reduceByKey(func: (V, V) => V, numPartitions: Int): RDD[(K, V)]

        函数说明
            可以将数据按照相同的 Key 对 Value 进行聚合




 */
object _24_Spark_Rdd_ReduceByKey {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("reduceBykey")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("b", 2), ("c", 3), ("c", 3)))

        rdd.reduceByKey((x: Int, y: Int) => {x + y})
        val newRdd: RDD[(String, Int)] = rdd.reduceByKey(_+_)


        val newRdd2: RDD[(String, Int)] = rdd.reduceByKey(_ + _, 2)

        println(newRdd.collect().mkString(","))
        println(newRdd2.collect().mkString(","))

        val rdd2 = sc.textFile("s00_spark_study/data/input/01_word.txt")

        val wordCount: RDD[(String, Int)] = rdd2.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)
        wordCount.collect().foreach(println)

        sc.stop()

    }

}








