package com.study.demo.spark_sql.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark_sql.movie.controller.Movie02Controller


object Movie02Application extends App with BaseApplication{


    val start = System.currentTimeMillis()


    startup("Movie02Application") {
        val controller = new Movie02Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)

}
