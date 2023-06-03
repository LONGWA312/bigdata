package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.functions._


/**
 * 4、年龄段在“18-24”的男人，最喜欢看 10 部电影
 */
class Movie04Service extends BaseService {

    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val filtedUsersDataFrame = userDataFrame
                .filter($"age" >= 18 and ($"age" <= 24) and($"gender" === "M"))

        val maleRatingAvgsDataFrame = ratingDataFrame
                .join(filtedUsersDataFrame, "userId")
                .groupBy("movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .sort("rating_avg")
                .limit(10)

        val result = maleRatingAvgsDataFrame
                .join(movieDataFrame, "movieId")
                .select("title", "rating_avg")

        result.show(false)

    }

    override def analysis2(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val result = ratingDataFrame
                .join(movieDataFrame, "movieId")
                .join(userDataFrame, "userId")
                .filter($"age" >= 18 and ($"age" <= 24) and ($"gender" === "M"))
                .groupBy("movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .sort($"rating_avg".desc)
                .limit(10)

        result.show(false)

    }


    override def analysis3(): Any = {

        val spark = EnvUtil.getSparkSession

        movieDao.readRatingsDataFrame().createOrReplaceTempView("ratings")
        movieDao.readUsersDataFrame().createOrReplaceTempView("users")
        movieDao.readMoviesDataFrame().createOrReplaceTempView("movies")

        val sql_result =
            """
              |SELECT
              |     m.title, COUNT(r.rating) rating_times, AVG(r.rating) rating_avg
              |FROM
              |     ratings r
              |JOIN
              |     users u
              |JOIN
              |     movies m
              |ON
              |     r.userId = u.userId AND r.movieId = m.movieId AND u.age >= 18 AND u.age <= 24 AND u.gender = 'M'
              |GROUP BY
              |     m.title
              |HAVING
              |     rating_times >= 50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT
              |     10
              |""".stripMargin

        spark.sql(sql_result).show()


    }
}
