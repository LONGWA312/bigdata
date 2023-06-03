package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie02Controller


/*
现有如此三份数据：
1、users.dat 数据格式为： 2::M::56::16::70072
对应字段为：UserID BigInt, Gender String, Age Int, Occupation String, Zipcode String
对应字段中文解释：用户 id，性别，年龄，职业，邮政编码

2、movies.dat 数据格式为： 2::Jumanji (1995)::Adventure|Children's|Fantasy
对应字段为：MovieID BigInt, Title String, Genres String
对应字段中文解释：电影 ID，电影名字，电影类型

3、ratings.dat 数据格式为： 1::1193::5::978300760
对应字段为：UserID BigInt, MovieID BigInt, Rating Double, Timestamped String
对应字段中文解释：用户 ID，电影 ID，评分，评分时间戳

需求：

2、分别求男性，女性当中评分最高的 10 部电影（性别，电影名，影评分）
 */
object Movie02Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie02Application") {
        val controller = new Movie02Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
