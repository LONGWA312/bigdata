package com.study.demo.common

import com.study.demo.utils.EnvUtil

trait BaseDao {

    def readJson(path: String) = {
        EnvUtil.getSparkSession.read.json(path)
    }

    def textFile(path: String) = {
        EnvUtil.getSparkContext.textFile(path)
    }
}
