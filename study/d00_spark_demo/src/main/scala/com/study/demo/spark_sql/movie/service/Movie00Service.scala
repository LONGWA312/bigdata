package com.study.demo.spark_sql.movie.service

import org.apache.spark.sql.functions._
import com.study.demo.spark_sql.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil

class Movie00Service extends BaseService{

    private val movieDao = new MovieDao()

    override def analysis(): Any = {
        println(this.getClass.getName)
    }

    def analysis1(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val ratingsDataFrame = movieDao.readRatingsDataFrame()
        val moviesDataFrame = movieDao.readMoviesDataFrame()

        ratingsDataFrame
                .groupBy("movieId")
                .agg(count("rating").as("rating_times"))
                .join(moviesDataFrame, "movieId")
                .select("title", "rating_times")
                .sort($"rating_times".desc)
                .show(10, false)

    }

}
