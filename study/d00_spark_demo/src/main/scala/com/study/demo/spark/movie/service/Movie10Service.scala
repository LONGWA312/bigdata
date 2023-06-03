package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ArrayBuffer

/*

需求：

10、各年评分最高的电影类型（年份，类型，影评分）
 */
class Movie10Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val movieRdd: RDD[Movie] = movieDao.readMovies()
        val ratingRdd: RDD[Rating] = movieDao.readRatings()


        /**
         *  input: ratingRdd
         *  -> map -> (movieId, rating)
         */
        val movieIdRatingRdd = ratingRdd.map(m => (m.movieId, m.rating))

        /**
         *  input: movieRdd
         *  -> map -> (movieId, (year, genres))
         */
        val movieIdYearGenresRdd = movieRdd.map(
            m => {
                val year = m.title.substring(m.title.length - 5, m.title.length - 1)
                (m.movieId, (year, m.genres))
            }
        )

        /**
         *  input:
         *  -> join: movieId -> (movieId, (rating, (year, genres)))
         *  -> map -> ((movieId, year, genres), rating)
         *  -> groupByKey().mapValues()  -> ((movieId, year, genres), rating_avg)
         *  -> map -> (year, genres, rating_avg)
         */
        val result = movieIdRatingRdd.join(movieIdYearGenresRdd).map(m => ((m._1, m._2._2._1, m._2._2._2), m._2._1)).groupByKey().mapValues(
            m => {
                m.sum * 1.0 / m.size
            }
        ).map(m => (m._1._2, m._1._3, m._2)).groupBy(_._1).mapValues(
            m => {
                m.toList.sortBy(_._3).reverse.head
            }
        ).map(_._2).sortBy(_._2, ascending = false)
        result.foreach(println)


    }



    def analysis2(): Unit ={


        //(movieID, year)
        val movieID_year: RDD[(String, String)] = movieDao.moviesRdd.map(x => (x._1, (x._2.substring(x._2.length - 5, x._2.length - 1))))

        //(movieID, rating)  ---> (movieID, Iterable(rating)) ---> (movieID, avg)
        val moviID_avg: RDD[(String, Double)] = movieDao.ratingsRdd.map(x => (x._2, x._3.toDouble)).groupByKey()
                .map(x => (x._1, x._2.sum / x._2.size))

        //关联movieID_year和moviID_avg   (movieID, (year, avg)) ---> (year, (movieID, avg))
        val year_mocvieID_avg: RDD[(String, (String, Double))] = movieID_year.join(moviID_avg)
                .distinct().map(x => (x._2._1, (x._1, x._2._2)))

        //(year, (movieID, avg))  ---> (year, Iterable((movieID, avg)))  ---> (movieID, (year, topavg))
        val year_movieID_topavg: RDD[(String, (String, Double))] = year_mocvieID_avg.groupByKey().map(x => {
            val list: List[(String, Double)] = x._2.toList.sortBy(-_._2)
            (list(0)._1, (x._1, list(0)._2))
        })

        //(movieID, (type, (year, topavg)) ---> (year, type, topavg)
        val year_type_topavg: RDD[(String, String, Double)] = movieDao.moviesRdd.map(x => (x._1, x._3))
                .join(year_movieID_topavg).map(x => (x._2._2._1, x._2._1, x._2._2._2)).sortBy(_._1, false)

        //输出结果
        year_type_topavg.foreach(println(_))
    }
}


