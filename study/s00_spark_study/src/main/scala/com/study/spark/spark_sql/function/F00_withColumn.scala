package com.study.spark.spark_sql.function

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types._

/**
 *
 * df.withColumn()
 *
 *      用于给dataframe添加字段
 */
object F00_withColumn {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder()
                .master("local[*]")
                .appName("Function_withColumn")
                .getOrCreate()
        import spark.implicits._


        val userRdd = spark.sparkContext
                .makeRDD(List(("Tom", 18), ("Jack", 19), ("Marry", 20)))
                .map(m => Row(m._1, m._2))

        val structType = StructType(
            Array(
                StructField("name", StringType, nullable = false),
                StructField("age", IntegerType, nullable = false)
            )
        )
        val userDataFrame = spark.createDataFrame(userRdd, structType)
        userDataFrame.show()

        testWithColumn1(userDataFrame)
        testWithColumn2(userDataFrame)
        testWithColumn3(userDataFrame)




    }

    /**
     * 使用functions.lit() 函数
     * 向dataframe中添加常量值的字段
     */
    def testWithColumn1(userDataFrame: DataFrame): Unit = {

        val colName = "gender"
        val constant = "Female"
        val userWithGenderDataFrame = userDataFrame.withColumn(colName, lit(constant))
        userWithGenderDataFrame.show()
    }


    /**
     * 向DataFrame中添加name的首字母
     * @param userDataFrame
     */
    def testWithColumn2(userDataFrame: DataFrame): Unit = {

        val newUserDataFrame = userDataFrame
                .withColumn("first", col("name").substr(0, 1))
        newUserDataFrame.show(false)
    }

    /**
     * 向DataFrame中添加name的后两位字母
     * @param userDataFrame
     */
    def testWithColumn3(userDataFrame: DataFrame): Unit = {

        val newUserDataFrame = userDataFrame
                .withColumn("first", col("name").substr(-2, 2))
        newUserDataFrame.show(false)
    }

}
