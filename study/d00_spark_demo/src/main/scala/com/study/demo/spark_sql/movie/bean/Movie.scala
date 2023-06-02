package com.study.demo.spark_sql.movie.bean

/**
 * 电影信息
 * @param movieId   电影Id
 * @param title     电影名
 * @param genres    类别
 */
case class Movie(
                  movieId: Long,
                  title: String,
                  genres: Array[String]
                )
