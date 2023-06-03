package com.study.demo.spark.movie.controller

import com.study.demo.common.BaseController
import com.study.demo.spark.movie.service.Movie06Service

class Movie06Controller extends BaseController{

    private val service = new Movie06Service()

    override def execute() = {

        val time = System.currentTimeMillis()
        service.analysis()
        val time0 = System.currentTimeMillis()
        println("**********************************\n analysis execute time: " + (time0 - time) / 1000d + "s\n**********************************")

        val time1 = System.currentTimeMillis()
        service.analysis2()
        val time2 = System.currentTimeMillis()
        println("**********************************\n analysis2 execute time: " + (time2 - time1) / 1000d + "s\n**********************************")

    }
}
