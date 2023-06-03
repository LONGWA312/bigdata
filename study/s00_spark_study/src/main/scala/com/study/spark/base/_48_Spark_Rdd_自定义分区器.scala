package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/*
    自定义分区器

        Spark提供了2个分区器，默认采用了Hash分区器
        分区器主要用于通过规则决定数据存放的分区位置
        为了能够准确地定位数据，可以来自己定义分区器规则

 */



object _048_Spark_Rdd_Partitioner {


    /*
        自定义分区器
        1.继承Partitioner类
        2.重写方法（2 + 2） (hashcode, equals)
     */
    class MyPartitioner(num: Int) extends Partitioner{

        /*
            分区数量
         */
        override def numPartitions: Int = {
            num
        }

        /*
            返回分区索引，根据每条数据的key来获取分区索引，索引号从0开始
         */
        override def getPartition(key: Any): Int = {
            key match {
                case "nba" => 0
                case "wnba" => 2
                case "cba" => 1
            }

        }
    }

    def main(args: Array[String]): Unit = {
        main()
    }

    def main(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[(String, String)] = sc.makeRDD(List(
            ("nba", "**************"),
            ("wnba", "**************"),
            ("cba", "**************"),
            ("nba", "**************")
        ))


        val newRdd: RDD[(String, String)] = rdd.partitionBy(new MyPartitioner(3))

        newRdd.saveAsTextFile("output")

        sc.stop()

    }

}
