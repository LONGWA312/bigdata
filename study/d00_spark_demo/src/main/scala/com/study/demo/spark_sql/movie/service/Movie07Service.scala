package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
 *求好片（评分>=4.0）最多的那个年份的最好看的 10 部电影
 */
class Movie07Service extends BaseService {

    private val movieDao = new MovieDao()
    private val spark: SparkSession = EnvUtil.getSparkSession

    override def analysis(): Any = {

        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val movieYearDatFrame = movieDataFrame
                .withColumn("year", col("title").substr(-5, 4))

        val joinRatingMovieDataFrame = ratingDataFrame
                .filter($"rating" >= 4.0)
                .join(movieYearDatFrame, "movieId")

        val year = joinRatingMovieDataFrame
                .groupBy("year")
                .count().as("count")
                .sort($"count".desc)
                .first().getAs[String]("year")

        val result = ratingDataFrame
                .groupBy("movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .join(movieYearDatFrame, "movieId")
                .filter($"year" === year)
                .sort($"rating_avg".desc)
                .select("movieId", "title", "rating_avg")
                .limit(10)
        result.show(false)
    }

    override def analysis2(): Any = {

        import spark.implicits._

        movieDao.readMoviesDataFrame().createOrReplaceTempView("movies")
        movieDao.readRatingsDataFrame().createOrReplaceTempView("ratings")
        movieDao.readUsersDataFrame().createOrReplaceTempView("users")



        val sql_year =
            """
              |SELECT
              |     SUBSTR(title, -5, 4) as year
              |FROM
              |     ratings r
              |JOIN
              |     movies m
              |ON
              |     r.movieId = m.movieId AND rating >= 4.0
              |GROUP BY
              |     year
              |ORDER BY
              |     COUNT(rating) DESC
              |LIMIT 1
              |
              |""".stripMargin

        val year = spark.sql(sql_year).first().getAs[String]("year")

        val sql_rating_year =
            s"""
               |SELECT
               |     r.movieId, t.title, t.year, COUNT(r.rating) rating_times, AVG(r.rating) rating_avg
               |FROM
               |     ratings r
               |JOIN
               |     (SELECT
               |         movieId, title, SUBSTR(title, -5, 4) as year
               |      FROM
               |         movies
               |     ) t
               |ON
               |     r.movieId = t.movieId
               |GROUP BY
               |     r.movieId, t.title, t.year
               |HAVING
               |     rating_times >= 50 AND year = $year
               |ORDER BY
               |    rating_avg DESC
               |LIMIT
               |    10
               |""".stripMargin

        spark.sql(sql_rating_year).show(false)


    }
}
