package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
 *求 1997 年上映的电影中，评分最高的 10 部 Comedy 类电影
 */
class Movie08Service extends BaseService {

    private val movieDao = new MovieDao()
    private val spark: SparkSession = EnvUtil.getSparkSession

    override def analysis(): Any = {

        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()

        val ratingAvgDataFrame = ratingDataFrame
                .groupBy("movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)

        val filterMovie1997 = movieDataFrame.filter($"title".contains("1997"))

        val result = ratingAvgDataFrame
                .join(filterMovie1997, "movieId")
                .filter(array_contains($"genres", "Comedy"))
                .sort($"rating_avg".desc)
                .limit(10)
                .select("movieId", "title", "genres", "rating_avg")

        result.show(false)

    }

    override def analysis2(): Any = {


        movieDao.readMoviesDataFrame().createOrReplaceTempView("movies")
        movieDao.readRatingsDataFrame().createOrReplaceTempView("ratings")

        val sql_rating_avgs =
            """
              |SELECT
              |     movieId, COUNT(rating) AS rating_times, AVG(rating) AS rating_avg
              |FROM
              |     ratings
              |GROUP BY
              |     movieId
              |HAVING
              |     rating_times >= 50
              |""".stripMargin

        spark.sql(sql_rating_avgs).createOrReplaceTempView("rating_avgs")

        val movie1997 =
            """
              |SELECT
              |     movieId, title, genres
              |FROM
              |     movies
              |WHERE
              |     title LIKE '%(1997)%'
              |""".stripMargin

        spark.sql(movie1997).createOrReplaceTempView("movie1997")

        val sql_result =
            """
              |SELECT
              |     r.movieId, m.title, m.genres, r.rating_avg
              |FROM
              |     rating_avgs r
              |JOIN
              |     movie1997 m
              |ON
              |     r.movieId = m.movieId AND ARRAY_CONTAINS(m.genres, "Comedy")
              |ORDER BY
              |     r.rating_avg DESC
              |LIMIT
              |     10
              |""".stripMargin

        spark.sql(sql_result).show(false)



    }
}
