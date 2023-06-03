package com.study.demo.spark_sql.movie.dao

import com.study.demo.common.BaseDao
import com.study.demo.spark_sql.movie.bean.{Movie, Rating, User}
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.{DataFrame, Dataset}

class MovieDao extends BaseDao{

    def readMoviesDataFrame(): DataFrame = {
        val path = "d00_spark_demo/data/input/movie/movies.json"
        val spark = EnvUtil.getSparkSession
        import spark.implicits._
        super.readJson(path).as[Movie].toDF()
    }


    def readRatingsDataFrame(): DataFrame = {
        val path = "d00_spark_demo/data/input/movie/ratings.json"
        val spark = EnvUtil.getSparkSession
        import spark.implicits._
        super.readJson(path).as[Rating].toDF()
    }


    def readUsersDataFrame(): DataFrame = {
        val path = "d00_spark_demo/data/input/movie/users.json"
        val spark = EnvUtil.getSparkSession
        import spark.implicits._
        super.readJson(path).as[User].toDF()
    }

}
