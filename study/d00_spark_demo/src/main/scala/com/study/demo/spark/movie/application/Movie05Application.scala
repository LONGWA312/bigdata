package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie05Controller


object Movie05Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie05Application") {
        val controller = new Movie05Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
