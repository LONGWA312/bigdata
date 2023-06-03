package com.study.demo.utils

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object EnvUtil {

    private val sparkContextLocal = new ThreadLocal[SparkContext]()
    private val sparkSessionLocal = new ThreadLocal[SparkSession]()


    def put(sparkContext: SparkContext): Unit = {
        sparkContextLocal.set(sparkContext)
    }


    def put(sparkSession: SparkSession): Unit = {
        sparkSessionLocal.set(sparkSession)
        sparkContextLocal.set(sparkSession.sparkContext)
    }


    def getSparkContext: SparkContext = {
        sparkContextLocal.get()
    }

    def getSparkSession: SparkSession = {
        sparkSessionLocal.get()
    }

    def clean(): Unit = {
        sparkSessionLocal.remove()
    }
}
