package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.rdd.RDD

/*

需求：

6、求最喜欢看电影（影评次数最多）的那位女性评最高分的 10 部电影的平均影评分（观影者，电影名，影评分）

 */
class Movie06Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val userRdd: RDD[User] = movieDao.readUsers()
        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val movieRdd: RDD[Movie] = movieDao.readMovies()

        /**
            input: ratingRdd
            -> map -> (userId, 1)
            -> reduceByKey -> (userId, times)
         */
        val userIdTimes = ratingRdd.map(m => (m.userId, 1)).reduceByKey(_ + _)

        /**
            input: userRdd
            -> filter: gender == "F"
            -> map -> (userId, gender)
         */
        val femaleUserId = userRdd.filter(_.gender == "F").map(m => (m.userId, m.gender))

        /**
            *input:
            *-> join: by userId -> (userId, (times, gender))
            *-> sortBy: times
            *-> first
         */
        val userId: Long = userIdTimes.join(femaleUserId).sortBy(_._2._1, false).first()._1

        /**
            *input: ratingRdd
            *-> filter: userId == userId
            *-> sortBy: rating, ascending=false
            *-> map -> (movieId)
            *-> take(10).toList -> List(movieId)
         */
        val top10Movies= ratingRdd.filter(_.userId == userId).sortBy(_.rating, ascending = false).map(_.movieId).take(10).toList

        /**
            *input: ratingRdd
            *-> filter: movieId in top10Movies -> ratingRdd
            *-> map -> Rdd(movieId, rating)
         */
        val movieIdRatingRdd = ratingRdd.filter(f => top10Movies.contains(f.movieId)).map(m => (m.movieId, m.rating))

        /**
            *input:
            *-> groupByKey: movieId -> (movieId, List(rating))
            -> mapValues: avg -> RDD(movieId, rating_avg)
         */
        val movieIdRatingAvgRdd = movieIdRatingRdd.groupByKey().mapValues(m => m.sum * 1.0d / m.size)

        /**
            *input: movieRdd
            *-> map -> RDD(movieId, title)
         */
        val movieIdTitleRdd = movieRdd.map(m => (m.movieId, m.title))

        /**
            *input:
            *-> join: by movieId -> RDD(movieId, (rating_avg, title))
            *-> map -> RDD(userId, title, rating_avg)
         */
        val result = movieIdRatingAvgRdd.join(movieIdTitleRdd).map(m => (userId, m._2._2, m._2._1)).repartition(1).sortBy(_._3, ascending = false)
        result.foreach(println)

        /**
        (1150,Night on Earth (1991),3.747422680412371)
        (1150,Lady and the Tramp (1955),3.8043981481481484)
        (1150,Roger & Me (1989),4.0739348370927315)
        (1150,Jungle Book, The (1967),3.816265060240964)
        (1150,It Happened One Night (1934),4.280748663101604)
        (1150,Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb (1963),4.4498902706656915)
        (1150,Duck Soup (1933),4.21043771043771)
        (1150,Rear Window (1954),4.476190476190476)
        (1150,Close Shave, A (1995),4.52054794520548)
        (1150,Being John Malkovich (1999),4.125390450691656)
         */

    }


    def analysis2(): Unit ={

        //(userID, Iterable(userID, movieID, rating, time_stamp))  ---> (userID, times)
        val userID_times: RDD[(String, Int)] = movieDao.ratingsRdd.groupBy(_._1).map(x => (x._1, x._2.size))

        //(userID, (sex, times))找到最喜欢看电影（影评次数最多）的那位女性的userID
        val userID: String = movieDao.usersRdd.map(x => (x._1, x._2)).join(userID_times).filter(_._2._1 == "F")
                .sortBy(_._2._2, false).map(_._1).first()

        //获得userID用户评分最高的10部电影的movieID
        val movieID: Array[(String, Int)] = movieDao.ratingsRdd.filter(_._1 == userID).map(x => (x._2, x._3.toInt))
                .sortBy(_._2, false).take(10)

        //获得该10部电影的平均影评分
        val movieID_rating: RDD[(String, String)] = movieDao.ratingsRdd.map(x => (x._2, x._3))

        //关联movieID和movieID_rating   (movieID, (rat1, rating))  ---> (movieID, Iterable(rating))  --> (movieID, avg)
        val movieID_avg = EnvUtil.getSparkContext.makeRDD(movieID).join(movieID_rating).map(x => (x._1, x._2._2.toInt))
                .groupByKey().map(x => {
            var avg = 0d
            if (x._2.size >= 50) {
                avg = x._2.sum * 1.0 / x._2.size
            }
            (x._1, avg)
        })

        //(movieID, (name, avg))   ---> (UserID, name, avg)
        val userID_name_avg: RDD[(String, String, Double)] = movieDao.moviesRdd.map(x => (x._1, x._2))
                .join(movieID_avg).map(x => (userID, x._2._1, x._2._2)).repartition(1).sortBy(_._3, false)

        userID_name_avg.foreach(println(_))

        /**
        (1150,Night on Earth (1991),3.747422680412371)
        (1150,Lady and the Tramp (1955),3.8043981481481484)
        (1150,Roger & Me (1989),4.0739348370927315)
        (1150,Jungle Book, The (1967),3.816265060240964)
        (1150,It Happened One Night (1934),4.280748663101604)
        (1150,Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb (1963),4.4498902706656915)
        (1150,Duck Soup (1933),4.21043771043771)
        (1150,Rear Window (1954),4.476190476190476)
        (1150,Close Shave, A (1995),4.52054794520548)
        (1150,Being John Malkovich (1999),4.125390450691656)
         */

    }
}
