package com.study.spark.base

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/*

广播变量： 分布式的只读变量

 */



object _050_Spark_Rdd_BC {


    def main(args: Array[String]): Unit = {
        main()
    }

    /*
        会产生shuffle和笛卡尔积
     */
    def main(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("io")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
        val list = List(("a", 4), ("b", 5), ("c", 6))

        // TODO 创建广播变量
        val bc: Broadcast[List[(String, Int)]] = sc.broadcast(list)

        rdd1.map{

            case (k, cnt) => {
                var tmp = 0

                // TODO 使用广播变量
                bc.value.foreach{
                    case (k1, cnt1) => {
                        if (k == k1) {
                            tmp = cnt1
                        }
                    }
                }
                (k, (cnt, tmp))
            }
        }.collect().foreach(println)


        sc.stop()

    }



}
