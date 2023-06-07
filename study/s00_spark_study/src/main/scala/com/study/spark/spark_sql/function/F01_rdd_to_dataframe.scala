package com.study.spark.spark_sql.function

import com.study.spark.spark_sql.function.test_bean.User
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}


object F01_rdd_to_dataframe {


    def main(args: Array[String]): Unit = {


        val spark: SparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("Function_withColumn")
                .getOrCreate()

        test_rdd_to_dataframe1(spark)
        spark.stop()
    }


    /**
     * 使用自定义样例类转换为dataframe
     * @param spark
     */
    def test_rdd_to_dataframe1(spark: SparkSession): Unit = {
        /*
            定义样例类
         */
        import spark.implicits._
        val user = spark.sparkContext
                .parallelize(Array(("Tom", 18), ("Jack", 19), ("Marry", 20)))
                .map(m => User(m._1, m._2))
                .toDF()
        user.show()

    }

}
