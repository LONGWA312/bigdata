package com.study.demo.spark_sql.common

import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession

trait BaseApplication {

    def startup(appName: String) (op: => Unit): Unit = {

        val sparkSession = SparkSession.builder().master("local[*]").appName(appName).getOrCreate()
        EnvUtil.put(sparkSession)

        try {
            op
        } catch {
            case ex: Exception => throw ex
        }

        sparkSession.stop()
        EnvUtil.clean()
    }
}
