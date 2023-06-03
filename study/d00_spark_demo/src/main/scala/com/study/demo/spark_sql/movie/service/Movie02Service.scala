package com.study.demo.spark_sql.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark_sql.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.sql.functions._


/**
 * 分别求男性，女性当中评分最高的 10 部电影（性别，电影名，影评分）
 */
class Movie02Service extends BaseService{

    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()


        userDataFrame.createOrReplaceTempView("users")
        ratingDataFrame.createOrReplaceTempView("ratings")
        movieDataFrame.createOrReplaceTempView("movies")

        val sqlString1 = """
          |SELECT
          |     u.gender, m.title,  r.rating
          |FROM
          |     ratings r
          |JOIN
          |     users u
          |ON
          |     r.userId = u.userId
          |JOIN
          |     movies m
          |ON
          |     m.movieId = r.movieId
          |
          |""".stripMargin

        val frame = spark.sql(sqlString1)
        frame.cache()
        frame.createOrReplaceTempView("gender_title_rating_table")


        val sqlString2 =
            """
              |SELECT
              |     gender, title, count(rating) rating_times, avg(rating) rating_avg
              |FROM
              |     gender_title_rating_table
              |WHERE
              |     gender = 'F'
              |GROUP BY
              |     gender, title
              |HAVING
              |     rating_times >= 50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT 10
              |""".stripMargin

        val sqlString3 =
            """
              |SELECT
              |     gender, title, count(rating) rating_times, avg(rating) rating_avg
              |FROM
              |     gender_title_rating_table
              |WHERE
              |     gender = 'M'
              |GROUP BY
              |     gender, title
              |HAVING
              |     rating_times >= 50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT 10
              |""".stripMargin

        spark.sql(sqlString2).show()
        spark.sql(sqlString3).show()

    }

    def analysis2(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val ratingMovieUserJoinedDataFrame = ratingDataFrame
                .join(movieDataFrame, "movieId")
                .join(userDataFrame, "userId")

        /**
         * 这里测试的加cache和不加没有多大的区别
         */
        ratingMovieUserJoinedDataFrame.cache()

        val femaleResult = ratingMovieUserJoinedDataFrame
                .filter($"gender" === "F")
                .groupBy("title", "gender")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .select("gender", "title", "rating_avg")
                .sort($"rating_avg".desc).take(10)

        val maleResult = ratingMovieUserJoinedDataFrame
                .filter($"gender" === "M")
                .groupBy("title", "gender")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .select("gender", "title", "rating_avg")
                .sort($"rating_avg".desc).take(10)

        val result = femaleResult.union(maleResult)
        result.foreach(println)
    }


    def analysis3(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val ratingMovieUserJoinedDataFrame = ratingDataFrame
                .join(movieDataFrame, "movieId")
                .join(userDataFrame, "userId")


        val titleGenderRatingAvgDataFrame = ratingMovieUserJoinedDataFrame
                .groupBy("title", "gender")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .select("gender", "title", "rating_avg")

        val femaleResult = titleGenderRatingAvgDataFrame.filter($"gender" === "F").sort($"rating_avg".desc).limit(10)
        val maleResult = titleGenderRatingAvgDataFrame.filter($"gender" === "M").sort($"rating_avg".desc).limit(10)
        val result = femaleResult.union(maleResult)
        result.show(20, false)


    }


    def analysis4(): Unit ={

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        val genderRatingAvgDataFrame = ratingDataFrame.join(userDataFrame, "userId")
                .groupBy("gender", "movieId")
                .agg(
                    count("rating").as("rating_times"),
                    avg("rating").as("rating_avg")
                )
                .filter($"rating_times" >= 50)
                .select("gender", "movieId", "rating_avg")

        val femaleResult = genderRatingAvgDataFrame.filter($"gender" === "F")
                .join(movieDataFrame, "movieId")
                .sort($"rating_avg".desc)
                .select("gender", "title", "rating_avg")
                .limit(10)

        val maleResult = genderRatingAvgDataFrame.filter($"gender" === "M")
                .join(movieDataFrame, "movieId")
                .sort($"rating_avg".desc)
                .select("gender", "title", "rating_avg")
                .limit(10)


        val result = femaleResult.union(maleResult)
        result.show(20, false)

    }

    def analysis5(): Any = {
        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()


        userDataFrame.createOrReplaceTempView("users")
        ratingDataFrame.createOrReplaceTempView("ratings")
        movieDataFrame.createOrReplaceTempView("movies")

        val sql_femaleUserId =
            """
              |SELECT
              |     userId
              |FROM
              |     users
              |WHERE
              |     gender='F'
              |""".stripMargin
        spark.sql(sql_femaleUserId).createOrReplaceTempView("female_userId")

        val sql_maleUserId =
            """
              |SELECT
              |     userId
              |FROM
              |     users
              |WHERE
              |     gender='M'
              |""".stripMargin
        spark.sql(sql_maleUserId).createOrReplaceTempView("male_userId")

        val sql_femaleRatingTop10 =
            """
              |SELECT
              |     movieId, COUNT(rating) rating_times, AVG(rating) rating_avg
              |FROM
              |     ratings r
              |JOIN
              |     female_userId f
              |ON
              |    r.userId = f.userId
              |GROUP BY
              |     movieId
              |HAVING
              |     rating_times >= 50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT
              |     10
              |
              |""".stripMargin

        spark.sql(sql_femaleRatingTop10).createOrReplaceTempView("female_rating_avg_top10")

        val sql_maleRatingTop10 =
            """
              |SELECT
              |     movieId, COUNT(rating) rating_times, AVG(rating) rating_avg
              |FROM
              |     ratings r
              |JOIN
              |     male_userId m
              |ON
              |    r.userId = m.userId
              |GROUP BY
              |     movieId
              |HAVING
              |     rating_times >=50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT
              |     10
              |
              |""".stripMargin
        spark.sql(sql_maleRatingTop10).createOrReplaceTempView("male_rating_avg_top10")

        val sql_femaleResult =
            """
              |SELECT
              |     'F' gender, m.title, f.rating_avg
              |FROM
              |     female_rating_avg_top10 f
              |JOIN
              |     movies m
              |ON
              |     f.movieId = m.movieId
              |ORDER BY
              |     f.rating_avg DESC
              |""".stripMargin
        spark.sql(sql_femaleResult).show(false)


        val sql_maleResult =
            """
              |SELECT
              |     'M' gender, m.title, f.rating_avg
              |FROM
              |     male_rating_avg_top10 f
              |JOIN
              |     movies m
              |ON
              |     f.movieId = m.movieId
              |ORDER BY
              |     f.rating_avg DESC
              |""".stripMargin
        spark.sql(sql_maleResult).show(false)

    }


    def analysis6(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        userDataFrame.createOrReplaceTempView("users")
        ratingDataFrame.createOrReplaceTempView("ratings")
        movieDataFrame.createOrReplaceTempView("movies")

        val sql_femaleRatingTop10 =
            """
              |SELECT
              |     movieId, COUNT(rating) rating_times, AVG(rating) rating_avg
              |FROM
              |     ratings
              |WHERE
              |     userId IN (SELECT userId FROM users WHERE gender='F')
              |GROUP BY
              |     movieId
              |HAVING
              |     rating_times >= 50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT
              |     10
              |
              |""".stripMargin

        spark.sql(sql_femaleRatingTop10).createOrReplaceTempView("female_rating_avg_top10")

        val sql_maleRatingTop10 =
            """
              |SELECT
              |     movieId, COUNT(rating) rating_times, AVG(rating) rating_avg
              |FROM
              |     ratings
              |WHERE
              |     userId IN (SELECT userId FROM users WHERE gender='M')
              |GROUP BY
              |     movieId
              |HAVING
              |     rating_times >=50
              |ORDER BY
              |     rating_avg DESC
              |LIMIT
              |     10
              |
              |""".stripMargin
        spark.sql(sql_maleRatingTop10).createOrReplaceTempView("male_rating_avg_top10")

        val sql_femaleResult =
            """
              |SELECT
              |     'F' gender, m.title, f.rating_avg
              |FROM
              |     female_rating_avg_top10 f
              |JOIN
              |     movies m
              |ON
              |     f.movieId = m.movieId
              |ORDER BY
              |     f.rating_avg DESC
              |""".stripMargin
        spark.sql(sql_femaleResult).show(false)


        val sql_maleResult =
            """
              |SELECT
              |     'M' gender, m.title, f.rating_avg
              |FROM
              |     male_rating_avg_top10 f
              |JOIN
              |     movies m
              |ON
              |     f.movieId = m.movieId
              |ORDER BY
              |     f.rating_avg DESC
              |""".stripMargin
        spark.sql(sql_maleResult).show(false)

    }


    def analysis7(): Any = {

        val spark = EnvUtil.getSparkSession
        import spark.implicits._

        val movieDataFrame = movieDao.readMoviesDataFrame()
        val ratingDataFrame = movieDao.readRatingsDataFrame()
        val userDataFrame = movieDao.readUsersDataFrame()

        userDataFrame.createOrReplaceTempView("users")
        ratingDataFrame.createOrReplaceTempView("ratings")
        movieDataFrame.createOrReplaceTempView("movies")


        val sql_table_join =
            """
              |SELECT
              | m.title
              |FROM
              |     ratings r
              |JOIN
              |     users u
              |JOIN
              |     movies m
              |ON
              |     r.movieId = m.movieId AND r.userId = u.userId
              |""".stripMargin
        spark.sql(sql_table_join).show(false)


    }
}
/*

[F,Close Shave, A (1995),4.644444444444445]
[F,Wrong Trousers, The (1993),4.588235294117647]
[F,Sunset Blvd. (a.k.a. Sunset Boulevard) (1950),4.572649572649572]
[F,Wallace & Gromit: The Best of Aardman Animation (1996),4.563106796116505]
[F,Schindler's List (1993),4.56260162601626]
[F,Shawshank Redemption, The (1994),4.539074960127592]
[F,Grand Day Out, A (1992),4.537878787878788]
[F,To Kill a Mockingbird (1962),4.536666666666667]
[F,Creature Comforts (1990),4.513888888888889]
[F,Usual Suspects, The (1995),4.513317191283293]

[M,Sanjuro (1962),4.639344262295082]
[M,Godfather, The (1972),4.583333333333333]
[M,Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954),4.576628352490421]
[M,Shawshank Redemption, The (1994),4.560625]
[M,Raiders of the Lost Ark (1981),4.520597322348094]
[M,Usual Suspects, The (1995),4.518248175182482]
[M,Star Wars: Episode IV - A New Hope (1977),4.495307167235495]
[M,Schindler's List (1993),4.49141503848431]
[M,Paths of Glory (1957),4.485148514851486]
[M,Wrong Trousers, The (1993),4.478260869565218]


[F,Close Shave, A (1995),4.644444444444445]
[F,Wrong Trousers, The (1993),4.588235294117647]
[F,Sunset Blvd. (a.k.a. Sunset Boulevard) (1950),4.572649572649572]
[F,Wallace & Gromit: The Best of Aardman Animation (1996),4.563106796116505]
[F,Schindler's List (1993),4.56260162601626]
[F,Shawshank Redemption, The (1994),4.539074960127592]
[F,Grand Day Out, A (1992),4.537878787878788]
[F,To Kill a Mockingbird (1962),4.536666666666667]
[F,Creature Comforts (1990),4.513888888888889]
[F,Usual Suspects, The (1995),4.513317191283293]

[M,Sanjuro (1962),4.639344262295082]
[M,Godfather, The (1972),4.583333333333333]
[M,Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954),4.576628352490421]
[M,Shawshank Redemption, The (1994),4.560625]
[M,Raiders of the Lost Ark (1981),4.520597322348094]
[M,Usual Suspects, The (1995),4.518248175182482]
[M,Star Wars: Episode IV - A New Hope (1977),4.495307167235495]
[M,Schindler's List (1993),4.49141503848431]
[M,Paths of Glory (1957),4.485148514851486]
[M,Wrong Trousers, The (1993),4.478260869565218]

 */

/*
(F,Close Shave, A (1995),4.644444444444445)
(F,Wrong Trousers, The (1993),4.588235294117647)
(F,Sunset Blvd. (a.k.a. Sunset Boulevard) (1950),4.572649572649572)
(F,Wallace & Gromit: The Best of Aardman Animation (1996),4.563106796116505)
(F,Schindler's List (1993),4.56260162601626)
(F,Shawshank Redemption, The (1994),4.539074960127592)
(F,Grand Day Out, A (1992),4.537878787878788)
(F,To Kill a Mockingbird (1962),4.536666666666667)
(F,Creature Comforts (1990),4.513888888888889)
(F,Usual Suspects, The (1995),4.513317191283293)

(M,Sanjuro (1962),4.639344262295082)
(M,Godfather, The (1972),4.583333333333333)
(M,Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954),4.576628352490421)
(M,Shawshank Redemption, The (1994),4.560625)
(M,Raiders of the Lost Ark (1981),4.520597322348094)
(M,Usual Suspects, The (1995),4.518248175182482)
(M,Star Wars: Episode IV - A New Hope (1977),4.495307167235495)
(M,Schindler's List (1993),4.49141503848431)
(M,Paths of Glory (1957),4.485148514851486)
(M,Wrong Trousers, The (1993),4.478260869565218)
 */
