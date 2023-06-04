package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


/**
 *6、求最喜欢看电影（影评次数最多）的那位女性评最高分的 10 部电影的平均影评分（观影者，电影名，影评分）
 */
class Movie06Service extends BaseService {

    private val movieDao = new MovieDao()
    private val spark: SparkSession = EnvUtil.getSparkSession

    override def analysis(): Any = {

        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val ratingAvgDataFrame = ratingDataFrame
                .groupBy("movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)


        val femaleUserDataFrame = userDataFrame.filter($"gender" === "F")

        val femaleTop1RatingTimes = ratingDataFrame
                .join(femaleUserDataFrame, "userId")
                .groupBy("userId")
                .agg(count("rating").as("rating_times"))
                .sort($"rating_times".desc)
                .limit(1)

        val result = ratingDataFrame
                .join(femaleTop1RatingTimes, "userId")
                .join(ratingAvgDataFrame, "movieId")
                .join(movieDataFrame, "movieId")
                .sort($"rating_avg".desc)
                .limit(10)
                .select("userId", "title", "rating_avg")
        result.show(false)

    }
}
