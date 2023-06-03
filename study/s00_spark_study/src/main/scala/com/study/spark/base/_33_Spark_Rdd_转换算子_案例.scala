package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*

    1) 数据准备
        agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔。
        1516609143867 6 7 64 16

    2) 需求描述
        统计出每一个省份每个广告被点击数量排行的 Top3
 */
object _33_Spark_Rdd_Test {


    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
        val sc: SparkContext = new SparkContext(sparkConf)

        val logs: RDD[String] = sc.textFile("s00_spark_study/data/input/agent.log")

        /*
            2. 将原始数据进行结构的转换。方便统计
                时间戳，省份，城市，用户，广告
                =>
                ( ( 省份，广告 ), 1 )
         */
        val logsRdd: RDD[((String, String), Int)] = logs.map(
            log => {
                val split = log.split(" ")
                val prv = split(1)
                val ad = split(4)
                ((prv, ad), 1)
            }
        )

        /*
            3. 将转换结构后的数据，进行分组聚合
                ( ( 省份，广告 ), 1 ) => ( ( 省份，广告 ), sum )
         */
        val reduceRdd: RDD[((String, String), Int)] = logsRdd.reduceByKey(_ + _)


        /*
            4. 将聚合的结果进行结构的转换
                ( ( 省份，广告 ), sum ) => ( 省份, ( 广告, sum ) )
         */
        val newMapRdd: RDD[(String, (String, Int))] = reduceRdd.map(
            data => {
                (data._1._1, (data._1._2, data._2))
            }
        )
        // 使用模式匹配的写法
        val newMapRdd2: RDD[(String, (String, Int))] = reduceRdd.map {
            case ((prv, ad), sum) => {
                (prv, (ad, sum))
            }
        }


        /*
             5. 将转换结构后的数据根据省份进行分组
                ( 省份, [( 广告A, sumA )，( 广告B, sumB )])
         */
        val groupRdd: RDD[(String, Iterable[(String, Int)])] = newMapRdd.groupByKey()


        /*
            6. 将分组后的数据组内排序（降序），取前3名
         */
        val resultRdd: RDD[(String, List[(String, Int)])] = groupRdd.mapValues(
            iter => {
                iter.toList.sortBy(_._2).reverse.take(3)
            }
        )

        resultRdd.collect().foreach(println)


    }
}
