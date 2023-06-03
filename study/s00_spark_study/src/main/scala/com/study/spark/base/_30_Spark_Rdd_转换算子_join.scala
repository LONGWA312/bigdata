package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    join

        函数签名：
            def join[W](other: RDD[(K, W)]): RDD[(K, (V, W))]

        函数说明
            在类型为(K,V)和(K,W)的 RDD 上调用，返回一个相同 key 对应的所有元素连接在一起的(K,(V,W))的 RDD




 */
object _30_Spark_Rdd_join {

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
            Array(("a", "A"), ("b", "B"), ("c", "C"))
        )
        val rdd4: RDD[(String, String)] = sc.makeRDD(
            Array(("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"), ("c", "F"))
        )

        val joinRdd1: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
        joinRdd1.collect().foreach(println)

        val joinRdd2: RDD[(String, (Int, String))] = rdd1.join(rdd3)
        joinRdd2.collect().foreach(println)

        val joinRdd3: RDD[(String, (Int, String))] = rdd1.join(rdd4)
        joinRdd3.collect().foreach(println)

        val joinRdd4: RDD[(String, (String, Int))] = rdd4.join(rdd1)
        joinRdd4.collect().foreach(println)

        val joinRdd5: RDD[(String, (String, Int))] = rdd4.join(rdd1)
        joinRdd5.collect().foreach(println)


        sc.stop()

    }

}








