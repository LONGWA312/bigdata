package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}


/*
    groupByKey

        函数签名：
            def groupByKey(): RDD[(K, Iterable[V])]
            def groupByKey(numPartitions: Int): RDD[(K, Iterable[V])]
            def groupByKey(partitioner: Partitioner): RDD[(K, Iterable[V])]


        函数说明
            将数据源的数据根据 key 对 value 进行分组
            返回的类型是KV类型， 第一个是分组的key, 第二个是相应的key的value的集合


        groupBykey对一个分区进行分组后，是不能继续往下执行
        需要所有的分区全部数据都分组完成后，才能继续往下执行
        所有在中间处理过程中，需要保存数据，但是又不能保存到内存中（因为rdd是不存储数据的）中，必须要落盘

        spark中的shuffle操作一定要落盘


        reduceByKey在shuffle前对分区内的数据事先进行聚合操作（combine预聚合），
        那么就可以减少shuffle落盘的数据量，较少io操作，从而提高shuffle的性能



        groupByKey后的mapValue功能应该也好好看看
        groupByKey的mapValue功能是对分组后的每个组的value进行操作



    reduceByKey 和 groupByKey 的区别？
        从 shuffle 的角度：
            reduceByKey 和 groupByKey 都存在 shuffle 的操作，
            但是 reduceByKey可以在 shuffle 前对分区内相同 key 的数据进行预聚合（combine）功能，这样会减少落盘的数据量，
            而 groupByKey 只是进行分组，不存在数据量减少的问题，reduceByKey 性能比较高。

        从功能的角度：
            reduceByKey 其实包含分组和聚合的功能。GroupByKey 只能分组，不能聚合，
            所以在分组聚合的场合下，推荐使用 reduceByKey，如果仅仅是分组而不需要聚合。那么还是只能使用 groupByKey





 */
object _25_Spark_Rdd_GroupByKey {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("groupByKey")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("b", 2), ("c", 3), ("c", 3)))

        val groupRdd: RDD[(String, Iterable[Int])] = rdd.groupByKey()
        groupRdd.collect().foreach(println)


        val groupRdd1: RDD[(String, Iterable[Int])] = rdd.groupByKey(2)
        groupRdd1.collect().foreach(println)


        val groupRdd2: RDD[(String, Iterable[Int])] = rdd.groupByKey(new HashPartitioner(2))
        groupRdd2.collect().foreach(println)


        /*
            如果使用分组聚合功能，则推荐使用reduceByKey
         */
        val reduceRdd: RDD[(String, Int)] = groupRdd2.mapValues(
            iter => {
                iter.sum
            }
        )
        reduceRdd.collect().foreach(println)


        val reduceRdd2: RDD[(String, Iterable[String])] = groupRdd2.mapValues(
            iter => {
                iter.map(m => {
                    "" + m
                })
            }
        )
        reduceRdd2.collect().foreach(println)

        sc.stop()

    }

}








