package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
 *求 1997 年上映的电影中，评分最高的 10 部 Comedy 类电影
 */
class Movie10Service extends BaseService {

    private val movieDao = new MovieDao()
    private val spark: SparkSession = EnvUtil.getSparkSession

    override def analysis(): Any = {

        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()


    }

    override def analysis2(): Any = {
        

    }
}
