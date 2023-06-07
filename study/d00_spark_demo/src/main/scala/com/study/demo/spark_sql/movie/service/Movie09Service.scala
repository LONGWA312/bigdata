package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
 * 该影评库中各种类型电影中评价最高的 5 部电影（类型，电影名，平均影评分）
 */
class Movie09Service extends BaseService {

    private val movieDao = new MovieDao()
    private val spark: SparkSession = EnvUtil.getSparkSession

    override def analysis(): Any = {

        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()

        movieDataFrame.withColumn("genre", explode($"genres")).drop("genres").show()



    }

    override def analysis2(): Any = {




    }
}
