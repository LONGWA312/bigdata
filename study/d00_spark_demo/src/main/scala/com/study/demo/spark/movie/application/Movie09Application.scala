package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie09Controller


object Movie09Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie09Application") {
        val controller = new Movie09Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
