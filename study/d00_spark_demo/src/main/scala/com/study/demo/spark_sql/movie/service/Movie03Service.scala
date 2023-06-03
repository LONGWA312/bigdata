package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.functions._


/**
 * 分别求男性，女性看过最多的 10 部电影（性别，电影名）
 */
class Movie03Service extends BaseService{

    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val movieRatingAvgDataFrame = ratingDataFrame
                .groupBy("movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)

        val movieGenderCountDataFrame = ratingDataFrame.join(userDataFrame, "userId")
                .groupBy("gender", "movieId")
                .agg(
                    count("rating").as("gender_times")
                )

        val genderTimesDataFrame = movieGenderCountDataFrame
                .join(movieDataFrame, "movieId")
//                .select("gender", "title", "gender_times")
        genderTimesDataFrame.cache()

        val femaleResult = genderTimesDataFrame.filter($"gender" === "F").sort($"gender_times".desc).limit(10).select("gender", "title", "gender_times")
        val maleResult = genderTimesDataFrame.filter($"gender" === "M").sort($"gender_times".desc).limit(10).select("gender", "title", "gender_times")
        femaleResult.show(false)
        maleResult.show(false)





    }

    override def analysis2(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        userDataFrame.createOrReplaceTempView("users")
        movieDataFrame.createOrReplaceTempView("movies")
        ratingDataFrame.createOrReplaceTempView("ratings")

        val sql_movieRatingTimes =
            """
              |SELECT
              |	    gender, t.movieId, COUNT(1) times
              |FROM (
              |	    SELECT
              |		    r.userId, r.movieId, u.gender, 1
              |	    FROM
              |		    ratings r
              |	    JOIN
              |		    users u
              |	    ON
              |		    r.userId = u.userId
              |) t
              |GROUP BY
              |	    t.gender, t.movieId
              |HAVING
              | 	times >= 50
              |
              |""".stripMargin

        spark.sql(sql_movieRatingTimes).createOrReplaceTempView("gender_movie_times")

        val sql_femaleTimes =
            """
              |SELECT
              |	    gmt.gender, m.title, gmt.times
              |FROM
              |		gender_movie_times gmt
              |JOIN
              |     movies m
              |ON
              |     gmt.movieId = m.movieId AND gmt.gender = 'F'
              |ORDER BY
              |		gmt.times DESC
              |LIMIT 10
              |""".stripMargin

        val sql_maleTimes =
            """
              |SELECT
              |	    gmt.gender, m.title, gmt.times
              |FROM
              |		gender_movie_times gmt
              |JOIN
              |     movies m
              |ON
              |     gmt.movieId = m.movieId AND gmt.gender = 'M'
              |ORDER BY
              |		gmt.times DESC
              |LIMIT 10
              |""".stripMargin

        spark.sql(sql_femaleTimes).show(false)
        spark.sql(sql_maleTimes).show(false)

    }


    override def analysis3(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()





    }


    override def analysis4(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()


    }
}
/*

+------+-----------------------------------------------------+------------+
|gender|title                                                |gender_times|
+------+-----------------------------------------------------+------------+
|F     |American Beauty (1999)                               |946         |
|F     |Shakespeare in Love (1998)                           |798         |
|F     |Silence of the Lambs, The (1991)                     |706         |
|F     |Sixth Sense, The (1999)                              |664         |
|F     |Groundhog Day (1993)                                 |658         |
|F     |Fargo (1996)                                         |657         |
|F     |Star Wars: Episode VI - Return of the Jedi (1983)    |653         |
|F     |Star Wars: Episode V - The Empire Strikes Back (1980)|648         |
|F     |Star Wars: Episode IV - A New Hope (1977)            |647         |
|F     |Forrest Gump (1994)                                  |644         |
+------+-----------------------------------------------------+------------+

+------+-----------------------------------------------------+------------+
|gender|title                                                |gender_times|
+------+-----------------------------------------------------+------------+
|M     |American Beauty (1999)                               |2482        |
|M     |Star Wars: Episode IV - A New Hope (1977)            |2344        |
|M     |Star Wars: Episode V - The Empire Strikes Back (1980)|2342        |
|M     |Star Wars: Episode VI - Return of the Jedi (1983)    |2230        |
|M     |Terminator 2: Judgment Day (1991)                    |2193        |
|M     |Jurassic Park (1993)                                 |2099        |
|M     |Saving Private Ryan (1998)                           |2078        |
|M     |Matrix, The (1999)                                   |2076        |
|M     |Men in Black (1997)                                  |2000        |
|M     |Back to the Future (1985)                            |1944        |
+------+-----------------------------------------------------+------------+




(F,American Beauty (1999),946)
(F,Shakespeare in Love (1998),798)
(F,Silence of the Lambs, The (1991),706)
(F,Sixth Sense, The (1999),664)
(F,Groundhog Day (1993),658)
(F,Fargo (1996),657)
(F,Star Wars: Episode VI - Return of the Jedi (1983),653)
(F,Star Wars: Episode V - The Empire Strikes Back (1980),648)
(F,Star Wars: Episode IV - A New Hope (1977),647)
(F,Forrest Gump (1994),644)


(M,American Beauty (1999),2482)
(M,Star Wars: Episode IV - A New Hope (1977),2344)
(M,Star Wars: Episode V - The Empire Strikes Back (1980),2342)
(M,Star Wars: Episode VI - Return of the Jedi (1983),2230)
(M,Terminator 2: Judgment Day (1991),2193)
(M,Jurassic Park (1993),2099)
(M,Saving Private Ryan (1998),2078)
(M,Matrix, The (1999),2076)
(M,Men in Black (1997),2000)
(M,Back to the Future (1985),1944)
 */
