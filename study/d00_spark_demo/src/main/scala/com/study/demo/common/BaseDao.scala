package com.study.demo.common

import com.study.demo.utils.EnvUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame

trait BaseDao {

    def readJson(path: String): DataFrame = {
        EnvUtil.getSparkSession.read.json(path)
    }

    def textFile(path: String): RDD[String] = {
        EnvUtil.getSparkContext.textFile(path)
    }
}
