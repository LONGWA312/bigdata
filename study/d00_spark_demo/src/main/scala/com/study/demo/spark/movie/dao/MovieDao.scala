package com.study.demo.spark.movie.dao

import com.study.demo.common.BaseDao
import org.apache.spark.rdd.RDD
import com.study.demo.spark.movie.bean.{Movie, Rating, User}

class MovieDao extends BaseDao{


    private def readText(path: String): RDD[Array[String]] = {
        super.textFile(path).map(_.split("::"))
    }

    def moviesRdd(): RDD[(String, String, String)] = {
        val path = "d00_spark_demo/data/input/movie/movies.dat"
        this.readText(path).map(m => (m(0), m(1), m(2)))
    }

    def ratingsRdd(): RDD[(String, String, String, String)] = {
        val path = "d00_spark_demo/data/input/movie/ratings.dat"
        this.readText(path).map(m => (m(0), m(1), m(2), m(3)))
    }

    def usersRdd(): RDD[(String, String, String, String, String)] = {
        val path = "d00_spark_demo/data/input/movie/users.dat"
        this.readText(path).map(m => (m(0), m(1), m(2), m(3), m(4)))
    }

    def readMovies(): RDD[Movie] = {
        this.moviesRdd.map(m => Movie(m._1.toLong, m._2, m._3))
    }
    def readRatings(): RDD[Rating] = {
        this.ratingsRdd().map(m => Rating(m._1.toLong, m._2.toLong, m._3.toDouble, m._4))
    }
    def readUsers(): RDD[User] = {
        this.usersRdd().map(m =>  User(m._1.toLong, m._2, m._3.toInt, m._4, m._5))
    }

}
