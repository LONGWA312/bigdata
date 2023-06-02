package com.study.demo.spark_sql.movie.controller

import com.study.demo.spark_sql.common.BaseController
import com.study.demo.spark_sql.movie.service.Movie00Service

class Movie00Controller extends BaseController{

    private val service = new Movie00Service()

    override def execute(): Unit = {
        val time0 = System.currentTimeMillis()
        service.analysis1()
        val time1 = System.currentTimeMillis()
        println("**********************************\n analysis1 execute time: " + (time1-time0)/1000d + "s\n**********************************")
    }
}
