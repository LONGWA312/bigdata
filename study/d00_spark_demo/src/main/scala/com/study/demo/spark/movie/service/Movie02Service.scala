package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ArrayBuffer



/*

需求：分别求男性，女性当中评分最高的 10 部电影（性别，电影名，影评分）
 */
class Movie02Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {


        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val userRdd: RDD[User] = movieDao.readUsers()
        val movieRdd: RDD[Movie] = movieDao.readMovies()

        /**
         * input: ratingRdd
         * -> map -> (userId, rating)
         */
        val userIdRatingRdd = ratingRdd.map(m => (m.userId, m))

        /**
         * input: userRdd
         * -> map -> (userId, gender)
         */
        val userIdGenderRdd = userRdd.map(m => (m.userId, m.gender))

        /**
         * input:
         * -> join: by userId -> (userId, (rating, gender))
         */
        val joinRdd = userIdRatingRdd.join(userIdGenderRdd)

        /**
         * input: (userId, (rating, gender))
         * -> map -> (gender, movieId), rating_value)
         */
        val genderMapRdd = joinRdd.map(
            m => {
                ((m._2._2, m._2._1.movieId), m._2._1.rating)
            }
        )

        /**
         * input: (gender, movieId), rating_value)
         * -> groupBYKey: (gender, movieId) -> ((gender, movieId), Array(rating_value))
         * -> mapValues -> ((gender, movieId), rating_avg)
         * -> map -> (movieId, (gender, rating_avg))
         */
        val movieIdGenderRatingAvgRdd: RDD[(Long, (String, Double))] = genderMapRdd.groupByKey().mapValues(
            m => {
                if (m.size > 50)
                    m.sum * 1.0 / m.size
                else
                    0d
            }
        ).map(m => (m._1._2, (m._1._1, m._2)))

        /**
         * input: movieRdd
         * -> map -> (movieId, title)
         */
        val movieIdNameRdd = movieRdd.map(m => (m.movieId, m.title))

        /**
         * input: (movieId, title), (movieId, (gender, rating_avg))
         * -> map -> (gender, (title, rating_avg))
         */
        val movieJoinRdd: RDD[(String, (String, Double))] = movieIdNameRdd.join(movieIdGenderRatingAvgRdd).map(m => (m._2._2._1, (m._2._1, m._2._2._2)))


        /**
         * input: (gender, (title, rating_avg))
         * -> groupByKey
         */
        val groupByGenderRdd = movieJoinRdd.groupByKey().mapValues(
            m => {
                m.toList.sortBy(_._2).reverse.take(10)
            }
        )
        groupByGenderRdd.foreach(println)

        val result: RDD[(String, String, Double)] = groupByGenderRdd.flatMap(
            m => {
                var array = new ArrayBuffer[(String, String, Double)]()
                val gender = m._1
                for (elem <- m._2.toArray) {
                    array.+=((gender, elem._1, elem._2))
                }
                array
            }
        )
        result.foreach(println)

    }


    override def analysis2(): Unit ={

        /**
         * 2、分别求男性，女性当中评分最高的 10 部电影（性别，电影名，影评分）
         */
        //(userID, sex)
        val userID_sex: RDD[(String, String)] = movieDao.usersRdd.map(x => (x._1, x._2))
        //(userID, (movieID, rating))
        val userID_movieID_rating: RDD[(String, (String, String))] = movieDao.ratingsRdd.map(x => (x._1, (x._2, x._3)))

        //(userID, (sex, (movieID, rating)))  ---> (sex, movieID, rating)
        val movieID_rating: RDD[(String, String, String)] = userID_sex.join(userID_movieID_rating).map(x => (x._2._1, x._2._2._1, x._2._2._2))

        //((sex, movieID), Iterable[(sex, movieID, rating)])  ---> (movieID, (sex, avg))
        val movieID_sex_avg: RDD[(String, (String, Double))] = movieID_rating.groupBy(x => (x._1, x._2)).map(x => {
            var sum, avg = 0d
            val list: List[(String, String, String)] = x._2.toList

            if (list.size > 50) {
                list.foreach(x => ( sum += x._3.toInt ))
                avg = sum * 1.0 / list.size
            }
            (x._1._2, (x._1._1, avg))
        })
        //(movieID, movieName)
        val movieID_movieName: RDD[(String, String)] = movieDao.moviesRdd.map(x => (x._1, x._2))
        //sex_movieID_avg与movie进行关联 (movieID, ((sex, avg), movieName)) ---> (sex, movieName, avg)
        val sex_movieName_avg: RDD[(String, String, Double)] = movieID_sex_avg.join(movieID_movieName)
                .map(x => (x._2._1._1, x._2._2, x._2._1._2)).sortBy(x => (x._1, x._3), false)

        sex_movieName_avg.take(10).foreach(println(_))
        sex_movieName_avg.filter(_._1 == "F").take(10).foreach(println(_))
    }
}
