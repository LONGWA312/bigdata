package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/*

RDD序列化

    1. RDD 血缘关系
        RDD 只支持粗粒度转换，即在大量记录上执行的单个操作。将创建 RDD 的一系列 Lineage（血统）记录下来，以便恢复丢失的分区。
        RDD 的 Lineage 会记录 RDD 的元数据信息和转换行为，当该 RDD 的部分分区数据丢失时，它可以根据这些信息来重新运算和恢复丢失的数据分区。

    2.  RDD 依赖关系
        这里所谓的依赖关系，其实就是两个相邻 RDD 之间的关系

    3. RDD 窄依赖
        窄依赖表示每一个父(上游)RDD 的 Partition 最多被子（下游）RDD 的一个 Partition 使用，窄依赖我们形象的比喻为独生子女。

    4. RDD 宽依赖
        宽依赖表示同一个父（上游）RDD 的 Partition 被多个子（下游）RDD 的 Partition 依赖，会引起 Shuffle，
        总结：宽依赖我们形象的比喻为多生。

    5. RDD 阶段划分
        DAG（Directed Acyclic Graph）有向无环图是由点和线组成的拓扑图形，该图形具有方向，不会闭环。例如，DAG 记录了 RDD 的转换过程和任务的阶段。

    6. RDD 任务划分
        RDD 任务切分中间分为：Application、Job、Stage 和 Task
        ⚫ Application：初始化一个 SparkContext 即生成一个 Application；
        ⚫ Job：一个 Action 算子就会生成一个 Job；
        ⚫ Stage：Stage 等于宽依赖(ShuffleDependency)的个数加 1；
        ⚫ Task：一个 Stage 阶段中，最后一个 RDD 的分区个数就是 Task 的个数。


    Spark为什么要有依赖关系
        shuffle会导致任务一分为二，
        shuffle会导致任务进行读写区分
        shuffle之前的阶段称之为写的阶段，shuffle之后的阶段称之为读阶段
        写的阶段没有执行完毕，那么读的阶段不能执行

 */



object _047_Spark_Rdd_Relation {


    def main(args: Array[String]): Unit = {
        main()
        main2()
    }

    /*
        血缘关系
     */
    def main(): Unit = {

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        val sc: SparkContext = new SparkContext(sparkConf)

        val fileRdd: RDD[String] = sc.textFile("s00_spark_study/data/input/1_word.txt")
        println(fileRdd.toDebugString)

        val wordRdd: RDD[String] = fileRdd.flatMap(line => line.split(" "))
        println(wordRdd.toDebugString)

        val mapRdd: RDD[(String, Int)] = wordRdd.map((_, 1))
        println(mapRdd.toDebugString)

        val wordCountRdd: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
        println(wordCountRdd.toDebugString)

        wordCountRdd.collect()

        sc.stop()

    }

    /*
        依赖关系
     */
    def main2(): Unit ={

        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

        val sc: SparkContext = new SparkContext(sparkConf)

        val fileRdd: RDD[String] = sc.textFile("s00_spark_study/data/input/1_word.txt")
        println(fileRdd.dependencies)

        val wordRdd: RDD[String] = fileRdd.flatMap(line => line.split(" "))
        println(wordRdd.dependencies)

        val mapRdd: RDD[(String, Int)] = wordRdd.map((_, 1))
        println(mapRdd.dependencies)

        val wordCountRdd: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)
        println(wordCountRdd.dependencies)

        wordCountRdd.collect()

        sc.stop()
    }
}
