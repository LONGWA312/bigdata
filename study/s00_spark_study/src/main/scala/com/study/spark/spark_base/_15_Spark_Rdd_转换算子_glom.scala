package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    glom()

    函数签名：
        def glom(): RDD[Array[T]]

    函数说明
        将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变


 */
object _15_Spark_Rdd_glom {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("glom")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)

        val glomRdd: RDD[Array[Int]] = rdd.glom()

        glomRdd.collect().foreach(data => println(data.mkString(",")))

        sc.stop()

    }

}



/*
    小功能：计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
 */
object Spark_Rdd_Glom_Test {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("glom")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 4, 3, 5, 6), 2)

        val glomRdd: RDD[Array[Int]] = rdd.glom()

        glomRdd.map(
            arr => arr.mkString(",")
        ).foreach(println)

        val maxRdd = glomRdd.map(
            array => {
                array.max
            }
        )

        println(maxRdd.collect().sum)

        sc.stop()

    }

}