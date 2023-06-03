package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    aggregateByKey

        函数签名：
            def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U,combOp: (U, U) => U): RDD[(K, U)]


        函数说明
            将数据根据不同的规则进行分区内计算和分区间计算


        可以对数据源的数据进行聚合，但是分区内和分区间的计算规则是独立的



    aggregateByKey算子的实现原理：
        




 */
object _26_Spark_Rdd_AggregateByKey {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("aggregateByKey")
        val sc = new SparkContext(sparkConf)



        val rdd: RDD[(String, Int)] = sc.makeRDD(
            List(
                ("a", 1), ("a", 2), ("b", 3), ("b", 4),
                ("a", 4), ("a", 5), ("b", 6), ("b", 7)
            ),
            2
        )
        /*
            取出每个分区内相同key的最大值，然后分区间相加
            0 - ("a", 2) ("b", 4)
            1 - ("a", 5) ("b", 7)
            ("a", 7), ("b", 11)

            aggregateByKey算在存在函数柯里化
            第一个参数列表有一个参数， 表示初始值
                用于在进行分区内计算是第一次key使用是的逻辑参数（如果只有分区内只有一个参数时，不能进行两两计算，那么就需要初始值）
            第二个参数列表有2个参数
                第一个参数表述分区内的计算规则（两两计算）
                第二个参数表示分区间的计算规则（两两计算）
         */
        val result: RDD[(String, Int)] = rdd.aggregateByKey(0)(
            //分区内的计算规则
            (x, y) => {
                math.max(x, y)
            },

            //分区间计算规则
            (x, y) => {
                x + y
            }
        )

        result.collect().foreach(println)

        val result1: RDD[(String, Int)] = rdd.aggregateByKey(0)(
            (x, y) => {
                x + y
            },
            (x, y) => {
                x + y
            }
        )
        result1.collect().foreach(println)

        //省略写法
        val result2 = rdd.aggregateByKey(0)(_ + _, _ + _)
        result2.collect().foreach(println)


        sc.stop()

    }

}








