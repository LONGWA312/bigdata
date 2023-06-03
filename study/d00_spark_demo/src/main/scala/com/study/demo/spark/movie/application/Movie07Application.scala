package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie07Controller


object Movie07Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie07Application") {
        val controller = new Movie07Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
