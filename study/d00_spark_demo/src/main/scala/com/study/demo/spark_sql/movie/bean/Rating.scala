package com.study.demo.spark_sql.movie.bean

/**
 * 影评信息
 * @param userId    用户ID
 * @param movieId   电影ID
 * @param rating    评分
 * @param timestamped   时间戳字符串
 */
case class Rating(
                   userId: Long,
                   movieId: Long,
                   rating: Double,
                   timestamped: String
                 )
