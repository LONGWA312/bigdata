package com.study.demo.spark_sql.common

import com.study.demo.utils.EnvUtil

trait BaseDao {

    def readJson(path: String) = {
        EnvUtil.getSparkSession.read.json(path)
    }
}
