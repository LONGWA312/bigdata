package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
 *5、求 movieid = 2116 这部电影各年龄段（因为年龄就只有 7 个，就按这个 7 个分就好了）的平均影评（年龄段，影评分）
 */
class Movie05Service extends BaseService {

    private val movieDao = new MovieDao()
    private val spark: SparkSession = EnvUtil.getSparkSession

    override def analysis(): Any = {

        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val filtedRatingsDataFrame = ratingDataFrame.filter($"movieId" === 2116)

        val result = filtedRatingsDataFrame
                .join(movieDataFrame, "movieId")
                .join(userDataFrame, "userId")
                .groupBy("age")
                .agg(
                    avg("rating").as("rating_avg")
                ).orderBy("age")
        result.show()

    }

    override def analysis2(): Any = {
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()


        val result = ratingDataFrame
                .join(movieDataFrame, "movieId")
                .join(userDataFrame, "userId")
                .filter($"movieId" === 2116)
                .groupBy("age")
                .agg(
                    avg("rating").as("rating_avg")
                ).orderBy("age")
        result.show()

    }

    override def analysis3(): Any = {


        movieDao.readRatingsDataFrame().createOrReplaceTempView("ratings")
        movieDao.readUsersDataFrame().createOrReplaceTempView("users")
        movieDao.readMoviesDataFrame().createOrReplaceTempView("movies")

        val sql_result =
            """
              |SELECT
              |     u.age, AVG(rating) rating_avg
              |FROM
              |     ratings r
              |JOIN
              |     users u
              |ON
              |     r.userId = u.userId AND r.movieId = 2116
              |GROUP BY
              |     u.age
              |ORDER BY
              |     u.age
              |""".stripMargin

        spark.sql(sql_result).show(false)

    }
}
