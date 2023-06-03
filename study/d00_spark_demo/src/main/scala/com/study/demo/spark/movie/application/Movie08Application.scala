package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie08Controller


object Movie08Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie08Application") {
        val controller = new Movie08Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
