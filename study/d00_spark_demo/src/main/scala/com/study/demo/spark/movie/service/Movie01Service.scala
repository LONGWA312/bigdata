package com.study.demo.spark.movie.service


import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD


/*
1、求被评分次数最多的 10 部电影，并给出评分次数（电影名，评分次数）
2、分别求男性，女性当中评分最高的 10 部电影（性别，电影名，影评分）
3、分别求男性，女性看过最多的 10 部电影（性别，电影名）
4、年龄段在“18-24”的男人，最喜欢看 10 部电影
5、求 movieid = 2116 这部电影各年龄段（因为年龄就只有 7 个，就按这个 7 个分就好了）的平均影评（年龄段，影评分）
6、求最喜欢看电影（影评次数最多）的那位女性评最高分的 10 部电影的平均影评分（观影者，电影名，影评分）
7、求好片（评分>=4.0）最多的那个年份的最好看的 10 部电影
8、求 1997 年上映的电影中，评分最高的 10 部 Comedy 类电影
9、该影评库中各种类型电影中评价最高的 5 部电影（类型，电影名，平均影评分）
10、各年评分最高的电影类型（年份，类型，影评分）
 */
class Movie01Service extends BaseService{

    private val movieDao = new MovieDao()


    override def analysis() = {

        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val movieRdd: RDD[Movie] = movieDao.readMovies()
        val movieIdRatingCountRdd: RDD[(Long, Int)] = ratingRdd.map(m => (m.movieId, 1)).reduceByKey(_ + _)
        val movieIdNameRdd: RDD[(Long, String)] = movieRdd.map(m => (m.movieId, m.title)).map(m => (m._1, m._2))
        val joinRdd: RDD[(Long, (Int, String))] = movieIdRatingCountRdd.join(movieIdNameRdd).sortBy(_._2._1, ascending = false)
        val result: Array[(String, Int)] = joinRdd.map(
            m => {
                (m._2._2, m._2._1)
            }
        ).take(10)
        result.foreach(println)
    }


    override def analysis2(data: Any): Any = {

        //获取电影id与对应的评分次数
        val movieID_rating: RDD[(String, Int)] = movieDao.ratingsRdd.map(x => (x._2, 1))
        val movieID_times: RDD[(String, Int)] = movieID_rating.reduceByKey(_ + _).sortBy(_._2, false)
        //获得电影id和电影名
        val movieID_name: RDD[(String, String)] = movieDao.moviesRdd.map(x => (x._1, x._2))
        //关联movieID_times和movieID_name，获得电影id，电影名，评分次数
        val result: RDD[(String, Int)] = movieID_times.join(movieID_name).sortBy(_._2._1, false).map(x => (x._2._2, x._2._1))
        //输出结果
        result.take(10).foreach(println(_))
    }
}
