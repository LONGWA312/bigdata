package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD


/*
现有如此三份数据：
1、users.dat 数据格式为： 2::M::56::16::70072
对应字段为：UserID BigInt, Gender String, Age Int, Occupation String, Zipcode String
对应字段中文解释：用户 id，性别，年龄，职业，邮政编码

2、movies.dat 数据格式为： 2::Jumanji (1995)::Adventure|Children's|Fantasy
对应字段为：MovieID BigInt, Title String, Genres String
对应字段中文解释：电影 ID，电影名字，电影类型

3、ratings.dat 数据格式为： 1::1193::5::978300760
对应字段为：UserID BigInt, MovieID BigInt, Rating Double, Timestamped String
对应字段中文解释：用户 ID，电影 ID，评分，评分时间戳

需求：

3、分别求男性，女性看过最多的 10 部电影（性别，电影名）

 */
class Movie03Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {


        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val userRdd: RDD[User] = movieDao.readUsers()
        val movieRdd: RDD[Movie] = movieDao.readMovies()

        val userIdMovieId = ratingRdd.map(m => (m.userId, m.movieId))
        val userIdGender = userRdd.map(m => (m.userId, m.gender))
        val movieIdTitle = movieRdd.map(m => (m.movieId, m.title))
        val joinRdd: RDD[(Long, (Long, String))] = userIdMovieId.join(userIdGender)
        val reduceByRdd = joinRdd.map(m => (m._2, 1)).reduceByKey(_ + _).map(m => (m._1._1, (m._1._2, m._2)))
        val joinMapRdd = reduceByRdd.join(movieIdTitle).map(m => (m._2._1._1, m._2._2, m._2._1._2))
        val resultF = joinMapRdd.filter(_._1 == "F").sortBy(_._3, ascending = false).take(10)
        val resultM = joinMapRdd.filter(_._1 == "M").sortBy(_._3, ascending = false).take(10)
        resultF.foreach(println)
        resultM.foreach(println)


    }


    def analysis2(): Unit ={

        val userID_sex: RDD[(String, String)] = movieDao.usersRdd.map(x => (x._1, x._2))
        //(userID, movieID)
        val userID_movieID: RDD[(String, String)] = movieDao.ratingsRdd.map(x => (x._1, x._2))
        //(movieID, name)
        val movieID_name: RDD[(String, String)] = movieDao.moviesRdd.map(x => (x._1, x._2))

        //(userID, (sex, movieID))  ---> (movieID, sex)
        val movieID_sex: RDD[(String, String)] = userID_sex.join(userID_movieID).map(x => (x._2._2, x._2._1))
        //关联movieID_sex和movieID_name    (movieID, (sex, name))  ---> (movieID, sex, name)
        val movieID_sex_name: RDD[(String, String, String)] = movieID_sex.join(movieID_name)
                .map(x => (x._1, x._2._1, x._2._2))

        //((sex, name), Iterable[(movieID, sex, name)])  ---> (sex, name, times)
        val sex_name_times: RDD[(String, String, Int)] = movieID_sex_name.groupBy(x => (x._2, x._3)).map(x => (x._1._1, x._1._2, x._2.toList.size)).sortBy(x => (x._1, x._3), false)
        //输出结果
        sex_name_times.take(10).foreach(println(_))
        sex_name_times.filter(_._1 == "F").take(10).foreach(println(_))

    }
}
