package com.study.demo.spark_sql.movie.service


import com.study.demo.spark_sql.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types
import org.apache.spark.sql.types.{LongType, StructField}


/**
 * 求被评分次数最多的 10 部电影，并给出评分次数（电影名，评分次数）
 */
class Movie01Service extends BaseService{

    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val spark = EnvUtil.getSparkSession

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()


        ratingDataFrame.createOrReplaceTempView("ratings")
        movieDataFrame.createOrReplaceTempView("movies")

        val sqlString = """
          |SELECT
          |     m.title AS name, count(r.rating) AS rating_times
          |FROM
          |     ratings r
          |JOIN
          |     movies m
          |ON
          |     r.movieId = m.movieId
          |GROUP BY name
          |ORDER BY rating_times DESC
          |LIMIT 10
          |""".stripMargin
        val result = spark.sql(sqlString)
        result.show(false)
    }


    def analysis2(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()


        val movieIdRatingAvgs  = ratingDataFrame.groupBy("movieId").agg(count("rating").as("rating_times")).sort($"rating_times".desc).take(10)
        val newRdd = spark.sparkContext.makeRDD(movieIdRatingAvgs)

        val structType = types.StructType(
            Array(
                StructField("movieId", LongType, nullable = false),
                StructField("rating_times", LongType, nullable = false)
            )
        )

        val movieIdRatingAvgDF = spark.createDataFrame(newRdd, structType)

        val result = movieIdRatingAvgDF.join(movieDataFrame, "movieId").select("title", "rating_times")

        result.show(false)

    }



    def analysis3(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()

        val result = ratingDataFrame.groupBy("movieId")
                .agg(count("rating").as("rating_times"))
                .join(movieDataFrame, "movieId")
                .select("title", "rating_times")
                .sort($"rating_times".desc)

        result.show(10, false)
    }



    def analysis4(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()

        val movieIdRatingTimesDataFrame = ratingDataFrame.join(movieDataFrame, "movieId")
                .groupBy("movieId")
                .agg(count("rating").as("rating_times"))

        val result = movieIdRatingTimesDataFrame.join(movieDataFrame, "movieId")
                .select("title", "rating_times")
                .sort($"rating_times".desc)

        result.show(10, false)

    }

    def analysis5(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()


        val movieIdRatingAvgs  = ratingDataFrame
                .groupBy("movieId")
                .agg(count("rating").as("rating_times"))
                .sort($"rating_times".desc).limit(10)


        val result = movieIdRatingAvgs
                .join(movieDataFrame, "movieId")
                .select("title", "rating_times")
                .sort($"rating_times".desc)

        result.show(false)

    }

    def analysis6(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()


        ratingDataFrame.createOrReplaceTempView("ratings")
        movieDataFrame.createOrReplaceTempView("movies")

        val sql_movie_rating_times = """
                          |SELECT
                          |     movieId, count(rating) AS rating_times
                          |FROM
                          |     ratings
                          |GROUP BY
                          |     movieId
                          |ORDER BY
                          |     rating_times DESC
                          |LIMIT 10
                          |""".stripMargin

        spark.sql(sql_movie_rating_times).createOrReplaceTempView("movie_rating_times")

        val sql_title_rating_times =
            """
              |SELECT
              |     m.title, mrt.rating_times
              |FROM
              |     movie_rating_times mrt
              |JOIN
              |     movies m
              |ON
              |     mrt.movieId = m.movieId
              |ORDER BY
              |     mrt.rating_times DESC
              |""".stripMargin

        spark.sql(sql_title_rating_times).show(false)
    }
}
/*

+-----------------------------------------------------+------------+
|title                                                |rating_times|
+-----------------------------------------------------+------------+
|American Beauty (1999)                               |3428        |
|Star Wars: Episode IV - A New Hope (1977)            |2991        |
|Star Wars: Episode V - The Empire Strikes Back (1980)|2990        |
|Star Wars: Episode VI - Return of the Jedi (1983)    |2883        |
|Jurassic Park (1993)                                 |2672        |
|Saving Private Ryan (1998)                           |2653        |
|Terminator 2: Judgment Day (1991)                    |2649        |
|Matrix, The (1999)                                   |2590        |
|Back to the Future (1985)                            |2583        |
|Silence of the Lambs, The (1991)                     |2578        |
+-----------------------------------------------------+------------+

 */
