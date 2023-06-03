package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD


/*
需求：

    年龄段在“18-24”的男人，最喜欢看 10 部电影

 */
class Movie04Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {


        val userRdd: RDD[User] = movieDao.readUsers()
        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val movieRdd: RDD[Movie] = movieDao.readMovies()

        /**
            input: userRdd  -> filter age>=18 and age<=24 and gender=M -> map -> output: (userId, (gender, age))
         */
        val filtedUserRdd = userRdd.filter(f => (f.age >= 18 && f.age <= 24 & f.gender == "M")).map(m => (m.userId, (m.gender, m.age)))

        /*
            input: ratingRdd -> map -> output: (userId, (movieId, rating))
         */
        val mapRatingRdd = ratingRdd.map(m => (m.userId, (m.movieId, m.rating)))

        /*
            input: -> join by userId -> output: (userId, ((movieId, rating), (gender, age))) -> map -> output: (movieId, rating)
         */
        val movieIdRatingRdd = mapRatingRdd.join(filtedUserRdd).map(m => m._2._1)

        /*
            input: (movieId, rating) -> groupbykey -> output: (movieId, List(rating)) -> mapvalues -> output: (movieId, rating_avg)
         */
        val movieIdRatingAvgRdd = movieIdRatingRdd.groupByKey().mapValues(
            m => {
                if (m.size > 50) {
                    m.sum * 1.0 / m.size
                } else {
                    0
                }
            }
        )

        /*
            input: movieRdd -> map -> output: (movieId, title)
         */
        val movieIdTitleRdd = movieRdd.map(m => (m.movieId, m.title))

        /*
            input:
                -> join by movieId -> output: (movieId, (rating_avg, title))
                -> map -> output: (movieId, title, rating_avg)
                -> sort by rating_avg  - > output: (movieId, title, rating_avg)
         */
        val sortedByRatingAvgRdd = movieIdRatingAvgRdd.join(movieIdTitleRdd).map(m => (m._1, m._2._2, m._2._1)).sortBy(_._3, ascending = false)

        /*
            input: (movieId, title, rating_avg) -> take(10)
         */
        val result = sortedByRatingAvgRdd.take(10)

        result.foreach(println)

    }


    override def analysis2(): Unit ={

        val userID_sex_age: RDD[(String, (String, Int))] = movieDao.usersRdd.map(x => (x._1, (x._2, x._3.toInt))).filter(x =>{
            x._2._2 >= 18 && x._2._2 <= 24 && x._2._1 == "M"
        } )

        //(userID, (movieID, rating))
        val userID_movieID_rating: RDD[(String, (String, Int))] = movieDao.ratingsRdd.map(x => (x._1, (x._2, x._3.toInt)))

        //关联userID与userID_movieID_rating    (userID, ((sex, age), (movieID, rating)))   ---> (movieID, rating)
        // --->(movieID, Iterable(movieID, rating))  ---> (movieID, avg)
        val movieID_avg : RDD[(String, Double)] = userID_sex_age.join(userID_movieID_rating).map(x => (x._2._2._1, x._2._2._2))
                .groupByKey().map(x => {
            var avg = 0d
            val len: Int = x._2.size
            if (len > 50){
                avg = 1.0 * x._2.sum / len
            }
            (x._1, avg)
        })

        //(movieID, name)
        val movieID_name: RDD[(String, String)] = movieDao.moviesRdd.map(x => (x._1, x._2))

        //关联movieID_avg与movieID_name    (movieID, (avg, name))
        val name_avg: RDD[(String, Double)] = movieID_avg.join(movieID_name).map(x => (x._2._2, x._2._1)).sortBy(_._2, false)

        //输出结果
        name_avg.take(10).foreach(println(_))

        /*
        (50,Usual Suspects, The (1995),4.7006578947368425)
        (318,Shawshank Redemption, The (1994),4.699708454810495)
        (2019,Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954),4.635135135135135)
        (858,Godfather, The (1972),4.570469798657718)
        (745,Close Shave, A (1995),4.550561797752809)
        (2858,American Beauty (1999),4.5406427221172025)
        (2571,Matrix, The (1999),4.534675615212528)
        (2324,Life Is Beautiful (La Vita � bella) (1997),4.52760736196319)
        (1193,One Flew Over the Cuckoo's Nest (1975),4.526041666666667)
        (1196,Star Wars: Episode V - The Empire Strikes Back (1980),4.5152838427947595)

        (Usual Suspects, The (1995),4.7006578947368425)
        (Shawshank Redemption, The (1994),4.699708454810495)
        (Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954),4.635135135135135)
        (Godfather, The (1972),4.570469798657718)
        (Close Shave, A (1995),4.550561797752809)
        (American Beauty (1999),4.5406427221172025)
        (Matrix, The (1999),4.534675615212528)
        (Life Is Beautiful (La Vita � bella) (1997),4.52760736196319)
        (One Flew Over the Cuckoo's Nest (1975),4.526041666666667)
        (Star Wars: Episode V - The Empire Strikes Back (1980),4.5152838427947595)
         */

    }
}
