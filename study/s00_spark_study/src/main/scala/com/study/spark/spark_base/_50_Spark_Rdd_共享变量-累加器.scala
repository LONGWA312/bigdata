package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/*

累加器

    累加器：分布式共享只写变量

    spark提供了累加器，可以将Excecutor端累加数据的结果传递会Driver
    因为累加器有多个，在计算完毕时，需要将累加器在Driver端合并到一起
    sc.longAccumulator("")

    累加器的功能可以做数值的累加，也可以做数据的累加




 */



object _050_Spark_Rdd_Acc {


    def main(args: Array[String]): Unit = {
        main1()
    }

    /*
        累加器
     */
    def main(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("io")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)

        /*
            创建累加器
         */
        var sum = sc.longAccumulator("sum")
        val sum1 = sc.doubleAccumulator("sum")
        sc.collectionAccumulator("")

        rdd.foreach(
            num => {
                //使用累加器
                sum.add(num)
            }
        )

        println("sum = " + sum.value)

        sc.stop()

    }

    /*
        自定义的累加器- wordCount
     */
    def main1(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("io")
        val sc: SparkContext = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("s00_spark_study/data/input/1_word.txt")

        //TODO 创建累加器
        val accumulator = new WordCountAccumulator

        //TODO 注册累加器
        sc.register(accumulator, "wordCount")

        //TODO 使用累加器
        rdd.foreach(
            line => {
                val words = line.split(" ")
                words.foreach(
                    word => {
                        accumulator.add(word)
                    }
                )
            }
        )

        // TODO 访问累加器
        println(accumulator.value)

        sc.stop()

    }


    /*
        自定义累加器 - WordCount

        1. 继承AccumulatorV2: 定义泛型
            输入： 累加器输入的数据类型
            输出： 累加器输出的数据类型

        2. 重写方法

     */
    class WordCountAccumulator extends AccumulatorV2[String,mutable.Map[String, Int]] {

        private val wordCountMap =  mutable.Map[String, Int]() //apply
//        private val wordCountMap =  mutable.Map[String, Int]

        //判断累加器是否为初始状态
        override def isZero: Boolean = {
            //判断map数据为空，则为初始状态
            wordCountMap.isEmpty
        }

        //复制累加器： 需要复制到节点
        override def copy(): AccumulatorV2[String, mutable.Map[String, Int]] = {
            new WordCountAccumulator()
        }

        //重置累加器
        override def reset(): Unit = {
            wordCountMap.clear()
        }

        // 累加数据: 单节点操作
        override def add(word: String): Unit = {
            val oldValue = wordCountMap.getOrElse(word, 0)
            wordCountMap.update(word, oldValue + 1)
        }

        //合并累加器
        override def merge(other: AccumulatorV2[String, mutable.Map[String, Int]]): Unit = {
            val map1 = wordCountMap
            val map2 = other.value

            map2.foreach{
                case (word, count) => {
                    val oldValue = map1.getOrElse(word, 0)
                    map1.update(word, oldValue + count)
                }
            }

        }

        //获取累加器的计算结果
        override def value: mutable.Map[String, Int] = {
            wordCountMap
        }
    }
}
