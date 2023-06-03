package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * flatMap
 *
 * 函数签名：
 *      def flatMap[U: ClassTag](f: T => TraversableOnce[U]): RDD[U]
 *
 * 函数说明
 *      将处理的数据进行扁平化后再进行映射处理，所以算子也称之为扁平映射
 *
 */
object _12_Spark_Rdd_flatMap {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("flatMap")

        val sc: SparkContext = new SparkContext(sparkConf)

        val dataRdd = sc.makeRDD(List(List(1, 2, 3, 4, 5), List(3, 4, 5, 6, 7)))

        val dataRdd1: RDD[Int] = dataRdd.flatMap(
            list => list
        )

        dataRdd1.foreach(println)

        sc.stop()

    }
}

object Spark_Rdd_FlatMap{


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("flapMap")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.makeRDD(List("abc", "def"))

        /*
            IN => string => abc, def
            Out => list => a,b,c,d,e,f
            String数组会被当作Char数组处理
         */
        val newRdd = rdd.flatMap(str => str)

        newRdd.collect().foreach(println)

        /*
            flatMap只会对最外层的集合做扁平化
         */
        val newRdd2 = sc.makeRDD(List(List(List(1, 2), List(3, 4)), List(List(5, 6), List(7, 8))))

        val result: RDD[List[Int]] = newRdd2.flatMap(
            list => list
        )

        result.collect().foreach(println)

        sc.stop()
    }
}



/*
    小功能：将 List(List(1,2),3,List(4,5))进行扁平化操作
 */
object Spark_Rdd_FlatMap_Test{

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("flapMap")

        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[Any] = sc.makeRDD(List(List(1, 2), 3, List(4, 5)))

        val newRdd: RDD[Any] = rdd.flatMap {
            case list: List[_] => list
            case int: Int => List(int)
        }

        newRdd.collect().foreach(println)

        sc.stop()

    }
}
