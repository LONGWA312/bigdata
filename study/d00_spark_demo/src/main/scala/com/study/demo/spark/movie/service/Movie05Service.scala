package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD


/*
需求：

    求 movieid = 2116 这部电影各年龄段（因为年龄就只有 7 个，就按这个 7 个分就好了）的平均影评（年龄段，影评分）

 */
class Movie05Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {


        val userRdd: RDD[User] = movieDao.readUsers()
        val ratingRdd: RDD[Rating] = movieDao.readRatings()

        /*
            input: ratingRdd -> filter by movieId=2116 -> map -> output: (userId, (movieId, rating))
         */
        val userIdMovieIdRatingRdd = ratingRdd.filter(f => (f.movieId == 2116)).map(m => (m.userId, (m.movieId, m.rating)))

        /*
            input: userRdd -> map -> output: (userId, age)
         */
        val userIdAgeRdd = userRdd.map(m => (m.userId, m.age))

        /*
            input: userIdMovieIdRatingRdd, userIdAgeRdd
                -> join by userId -> output: (userId, ((movieId, rating), age))
                -> map -> (age, rating)
         */
        val ageRatingRdd = userIdMovieIdRatingRdd.join(userIdAgeRdd).map(m => (m._2._2, m._2._1._2))

        /*
            input: (age, rating)
                -> group by age -> output: (age, List(rating))
                -> mapvalues -> output: (age, rating_avg)
                -> sort by age -> output: (age, rating_avg)
         */
        val result = ageRatingRdd.groupByKey().mapValues(
            m => {
                m.sum * 1.0 / m.size
            }
        ).sortByKey()

        result.foreach(println)
        /*

           (1,3.2941176470588234)
           (45,2.8275862068965516)
           (18,3.3580246913580245)
           (25,3.436548223350254)
           (35,3.2278481012658227)
           (50,3.32)
           (56,3.5)
        */
    }


    def analysis2(): Unit ={

        // 获得movieID = 2116  (userID, rating)
        val userID_rating: RDD[(String, Int)] = movieDao.ratingsRdd.filter(_._2 == "2116").map(x => (x._1, x._3.toInt))
        //(userID, age)
        val userID_age: RDD[(String, String)] = movieDao.usersRdd.map(x => (x._1, x._3))
        //关联userID_age和userID_rating   (userID, (age, rating)) --->(age, rating)  ---> (age, Iterable(rating))
        val age_avg: RDD[(String, Double)] = userID_age.join(userID_rating).map(x => (x._2._1, x._2._2)).groupByKey()
                .map(x => (x._1, x._2.sum * 1.0 / x._2.size))

        //输出结果
        age_avg.sortByKey().foreach(println(_))

        /*

            (1,3.2941176470588234)
            (45,2.8275862068965516)
            (18,3.3580246913580245)
            (25,3.436548223350254)
            (35,3.2278481012658227)
            (50,3.32)
            (56,3.5)

         */

    }
}
