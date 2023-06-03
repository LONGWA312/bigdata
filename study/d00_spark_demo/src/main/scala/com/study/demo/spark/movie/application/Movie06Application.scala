package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie06Controller


object Movie06Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie06Application") {
        val controller = new Movie06Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
