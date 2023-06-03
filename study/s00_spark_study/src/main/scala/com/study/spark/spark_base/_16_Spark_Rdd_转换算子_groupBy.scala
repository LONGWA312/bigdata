package com.study.spark.base

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*
    groupBy()

    函数签名：
        def groupBy[K](f: T => K)(implicit kt: ClassTag[K]): RDD[(K, Iterable[T])]

    函数说明
        将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样的操作称之为 shuffle。极限情况下，数据可能被分在同一个分区中
        一个组的数据在一个分区中，但是并不是说一个分区中只有一个组


 */
object _16_Spark_Rdd_GroupBy {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("glom")
        val sc = new SparkContext(sparkConf)

        val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)


        /*
            groupBy会将数据源中的每一个数据进行分组，根据返回的分组key进行分组
            相同的key值数据，会放置在一个组中
         */
        def groupFunction(num: Int) = {
            num % 2
        }

        val groupRdd: RDD[(Int, Iterable[Int])] = rdd.groupBy(groupFunction)

        groupRdd.collect().foreach(println)

        sc.stop()

    }

}



/*
    小功能：将 List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组。

 */
object Spark_Rdd_GroupBy_Test {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("glom")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"))

        def groupFunction(word: String) =  {
            word(0)
        }

        val groupRdd: RDD[(Char, Iterable[String])] = rdd.groupBy(groupFunction)

        groupRdd.collect().foreach(println)

        sc.stop()

    }

}


/*
    小功能：从服务器日志数据 apache.log 中获取每个时间段访问量。
 */
object Spark_Rdd_GroupBy_Test2{

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("groupBy")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("s00_spark_study/data/input/apache.log")

        def groupFunction(log: String): String ={
            val split = log.split(" ")
            val dateTime = split(3)
            val format = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
            val date = format.parse(dateTime)

            val format1 = new SimpleDateFormat("HH")
            val hour: String = format1.format(date)
            hour
        }

        val groupRdd = rdd.groupBy(groupFunction)
        groupRdd.collect().foreach(println)


        val result = groupRdd.map(
            data => {
                (data._1, data._2.size)
            }
        )
        result.collect().foreach(println)

        sc.stop()

    }

}


/*
    小功能：从服务器日志数据 apache.log 中获取每个时间段访问量。实现二
 */
object Spark_Rdd_GroupBy_Test3{

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("groupBy")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("spark_study\\src\\main\\input\\apache.log")

        val hourData: RDD[(String, Int)] = rdd.map(
            line => {
                val split = line.split(" ")
                val timeStr = split(3)
                val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
                val date = sdf.parse(timeStr)

                val sdf1 = new SimpleDateFormat("HH")
                val hour: String = sdf1.format(date)
                (hour, 1)
            }
        )

        val groupRdd: RDD[(String, Iterable[(String, Int)])] = hourData.groupBy(_._1)

        val result = groupRdd.map(
            data => (data._1, data._2.size)
        )

        result.collect().sortBy(_._1).foreach(println)

        sc.stop()

    }

}


/*
    小功能：从服务器日志数据 apache.log 中获取每个时间段访问量。实现三 使用reducebykey实现
 */

object Spark_Rdd_ReduceBykey_Test4 {

    def main(args: Array[String]): Unit = {


        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("groupBy")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("spark_study\\src\\main\\input\\apache.log")

        val hoursRdd: RDD[(String, Int)] = rdd.map(line => {

            val timeStr = line.split(" ")(3)
            val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
            val date = sdf.parse(timeStr)
            val sdf2 = new SimpleDateFormat("HH")
            val hour = sdf2.format(date)
            (hour, 1)
        })

        val result: RDD[(String, Int)] = hoursRdd.reduceByKey(_ + _)
        result.collect().sortBy(_._1).foreach(println)



    }

}


