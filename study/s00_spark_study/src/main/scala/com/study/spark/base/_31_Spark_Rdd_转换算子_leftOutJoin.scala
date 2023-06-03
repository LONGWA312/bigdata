package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    leftOutJoin

        函数签名：
            def leftOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (V, Option[W]))]

        函数说明
            类似于 SQL 语句的左外连接


 */
object _31_Spark_Rdd_leftOutJoin {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("join")
        val sc = new SparkContext(sparkConf)

        val rdd1: RDD[(String, Int)] = sc.makeRDD(
            Array(("a", 1), ("b", 1), ("c", 1))
        )
        val rdd2: RDD[(String, Int)] = sc.makeRDD(
            Array(("a", 2), ("b", 2), ("c", 2))
        )
        val rdd3: RDD[(String, String)] = sc.makeRDD(
            Array(("b", "B"), ("c", "C"), ("d", "D"))
        )
        val rdd4: RDD[(String, String)] = sc.makeRDD(
            Array(("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"), ("c", "F"))
        )

        val joinRdd1: RDD[(String, (Int, Option[String]))] = rdd1.leftOuterJoin(rdd4)
        joinRdd1.collect().foreach(println)

        val joinRdd2: RDD[(String, (String, Option[Int]))] = rdd4.leftOuterJoin(rdd1)
        joinRdd2.collect().foreach(println)

        val joinRdd3: RDD[(String, (Int, Option[String]))] = rdd2.leftOuterJoin(rdd3)
        joinRdd3.collect().foreach(println)

        val joinRdd4: RDD[(String, (String, Option[Int]))] = rdd3.leftOuterJoin(rdd2)
        joinRdd4.collect().foreach(println)

        val filterRdd: RDD[(String, (String, Option[Int]))] = joinRdd4.filter(
            data => {
                data._2._2.isDefined
            }
        )
        filterRdd.collect().foreach(println)

        sc.stop()
    }

}








