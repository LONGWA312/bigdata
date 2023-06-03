package com.study.demo.spark_sql.movie.controller

import com.study.demo.common.BaseController
import com.study.demo.spark_sql.movie.service.{Movie04Service, Movie05Service}


class Movie05Controller extends BaseController {

    private val service = new Movie05Service()

    override def execute(): Unit = {

        val time = System.currentTimeMillis()
        service.analysis()
        val time0 = System.currentTimeMillis()
        println("**********************************\n analysis execute time: " + (time0-time)/1000d + "s\n**********************************")

        val time1 = System.currentTimeMillis()
        service.analysis2()
        val time2 = System.currentTimeMillis()
        println("**********************************\n analysis2 execute time: " + (time2-time1)/1000d + "s\n**********************************")

        val time3 = System.currentTimeMillis()
        service.analysis3()
        val time4 = System.currentTimeMillis()
        println("**********************************\n analysis3 execute time: " + (time4-time3)/1000d + "s\n**********************************")

        val time5 = System.currentTimeMillis()
        service.analysis4()
        val time6 = System.currentTimeMillis()
        println("**********************************\n analysis4 execute time: " + (time6-time5)/1000d + "s\n**********************************")


    }
}
