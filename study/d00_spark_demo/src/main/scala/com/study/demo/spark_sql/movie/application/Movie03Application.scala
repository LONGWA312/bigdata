package com.study.demo.spark_sql.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark_sql.movie.controller.Movie03Controller


object Movie03Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie03Application") {
        val controller = new Movie03Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)

}
