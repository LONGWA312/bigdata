package com.study.demo.spark_sql.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark_sql.movie.controller.{Movie05Controller, Movie06Controller}


object Movie06Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie06Application") {
        val controller = new Movie06Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)

}
