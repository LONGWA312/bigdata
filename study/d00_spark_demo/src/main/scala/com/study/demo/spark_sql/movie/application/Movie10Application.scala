package com.study.demo.spark_sql.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark_sql.movie.controller.{Movie09Controller, Movie10Controller}


object Movie10Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie10Application") {
        val controller = new Movie10Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)

}
