package com.study.demo.spark_sql.movie.application

import com.study.demo.spark_sql.common.BaseApplication
import com.study.demo.spark_sql.movie.controller.Movie00Controller

object Movie00Application extends App with BaseApplication{

    private val start = System.currentTimeMillis()

    startup("Movie00Application") {
        val controller = new Movie00Controller()
        controller.execute()
    }

    private val end = System.currentTimeMillis()
    println("app execute time: " + (end - start) / 1000 + "s")

}
