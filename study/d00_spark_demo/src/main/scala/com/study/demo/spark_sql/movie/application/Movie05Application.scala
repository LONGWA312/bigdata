package com.study.demo.spark_sql.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark_sql.movie.controller.{Movie04Controller, Movie05Controller}


object Movie05Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie04Application") {
        val controller = new Movie05Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)

}
