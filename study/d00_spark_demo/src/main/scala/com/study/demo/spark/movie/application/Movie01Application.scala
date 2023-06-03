package com.study.demo.spark.movie.application

import com.study.demo.common.BaseApplication
import com.study.demo.spark.movie.controller.Movie01Controller


/*

需求：

1、求被评分次数最多的 10 部电影，并给出评分次数（电影名，评分次数）
2、分别求男性，女性当中评分最高的 10 部电影（性别，电影名，影评分）
3、分别求男性，女性看过最多的 10 部电影（性别，电影名）
4、年龄段在“18-24”的男人，最喜欢看 10 部电影
5、求 movieid = 2116 这部电影各年龄段（因为年龄就只有 7 个，就按这个 7 个分就好了）
的平均影评（年龄段，影评分）
6、求最喜欢看电影（影评次数最多）的那位女性评最高分的 10 部电影的平均影评分（观影者，电影名，影评分）
7、求好片（评分>=4.0）最多的那个年份的最好看的 10 部电影
8、求 1997 年上映的电影中，评分最高的 10 部 Comedy 类电影
9、该影评库中各种类型电影中评价最高的 5 部电影（类型，电影名，平均影评分）
10、各年评分最高的电影类型（年份，类型，影评分）
 */
object Movie01Application extends App with BaseApplication{


    val start = System.currentTimeMillis()

    startup("Movie01Application") {
        val controller = new Movie01Controller()
        controller.execute()
    }

    val end = System.currentTimeMillis()
    println("time = " + (end - start) / 1000)
}
