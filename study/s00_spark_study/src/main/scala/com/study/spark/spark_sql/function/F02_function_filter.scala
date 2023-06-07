package com.study.spark.spark_sql.function

import com.study.spark.spark_sql.bean.Movie
import org.apache.spark.sql.SparkSession


/**
 * 集合操作函数
 */
object F02_function_filter {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").appName("function_array_operation").getOrCreate()
        import  spark.implicits._

        val movies = spark.read.json("s00_spark_study/data/input/movies.json").as[Movie]
        movies.show(false)


        /**
         * 单列过滤
         * 字符串判断：判断是否包含
         */
        movies.filter($"title".contains("1997")).show(5, false)


        /**
         * 单列过滤
         * 字符串判断：判断以什么开始，或结尾 （starsWith, endWith）
         */
        movies.filter($"title".startsWith("B")).show(5, false)
        movies.filter($"title".endsWith("(1995)")).show(5, false)


        /**
         * 单列过滤
         * 字符串判断：等值判断 （===）
         */




    }


}
