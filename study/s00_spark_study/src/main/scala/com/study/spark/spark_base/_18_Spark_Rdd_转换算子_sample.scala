package com.study.spark.base

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    sample

    函数签名：
        def sample(
             withReplacement: Boolean,
             fraction: Double,
             seed: Long = Utils.random.nextLong): RDD[T]

    函数说明
        根据指定的规则从数据集中抽取数据

     sample算子需要传递三个参数
     1. 第一个参数表示，抽取数据后是否将数据返回 true（放回），false（丢弃）
     2. 第二个参数表示，
             如果抽取不放回的场合：数据源中每条数据被抽取的概率，基准值的概念
             如果抽取放回的场合：表示数据源中的每条数据被抽取的可能次数
     3. 第三个参数表示，抽取数据时随机算法的种子
                        如果不传递第三个参数，那么使用的是当前系统时间

 */
object _18_Spark_Rdd_Sample {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("filter")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 1)

        /*
             抽取数据不放回（伯努利算法）
             伯努利算法：又叫 0、1 分布。例如扔硬币，要么正面，要么反面。
             具体实现：根据种子和随机算法算出一个数和第二个参数设置几率比较，小于第二个参数要，大于不要
             第一个参数：抽取的数据是否放回，false：不放回
             第二个参数：抽取的几率，范围在[0,1]之间,0：全不取；1：全取；
             第三个参数：随机数种子
         */
        val sampleRdd: RDD[Int] = rdd.sample(false, 0.5)
        val sampleRdd1: RDD[Int] = rdd.sample(false, 0.5, 1)

        println(sampleRdd.collect().mkString(","))
        println(sampleRdd1.collect().mkString(","))



        /*
             抽取数据放回（泊松算法）
                 第一个参数：抽取的数据是否放回，true：放回；false：不放回
                 第二个参数：重复数据的几率，范围大于等于 0.表示每一个元素被期望抽取到的次数
                 第三个参数：随机数种子
         */
        val sampleRdd2: RDD[Int] = rdd.sample(true, 2)
        val sampleRdd3 = rdd.sample(true, 0.1, 1)

        println(sampleRdd2.collect().mkString(","))
        println(sampleRdd3.collect().mkString(","))

        sc.stop()

    }

}







