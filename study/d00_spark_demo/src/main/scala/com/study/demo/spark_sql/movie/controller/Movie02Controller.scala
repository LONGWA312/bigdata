package com.study.demo.spark_sql.movie.controller

import com.study.demo.spark_sql.common.BaseController
import com.study.demo.spark_sql.movie.service.Movie02Service


class Movie02Controller extends BaseController {

    private val service = new Movie02Service()

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

        val time7 = System.currentTimeMillis()
        service.analysis5()
        val time8 = System.currentTimeMillis()
        println("**********************************\n analysis5 execute time: " + (time8-time7)/1000d + "s\n**********************************")

        val time9 = System.currentTimeMillis()
        service.analysis6()
        val time10 = System.currentTimeMillis()
        println("**********************************\n analysis6 execute time: " + (time10-time9)/1000d + "s\n**********************************")
        val time11 = System.currentTimeMillis()
        service.analysis7()
        val time12 = System.currentTimeMillis()
        println("**********************************\n analysis7 execute time: " + (time12-time11)/1000d + "s\n**********************************")

    }
}
