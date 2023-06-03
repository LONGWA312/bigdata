package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ArrayBuffer

/*

需求：

9、该影评库中各种类型电影中评价最高的 5 部电影（类型，电影名，平均影评分）
 */
class Movie09Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {
        val start = System.currentTimeMillis()

        val movieRdd: RDD[Movie] = movieDao.readMovies()
        val ratingRdd: RDD[Rating] = movieDao.readRatings()

        val end = System.currentTimeMillis()
        println()
        println("analysis time: " + {end - start} + "ms")
        /**
         *  input: movieRdd
         *  -> map -> (movieId, title, Array(genre))
         *  -> flatmap -> (movieId, (title, genre))
         */
        val movieIdTitleGenreRdd = movieRdd.map(m => (m.movieId, m.title, m.genres.split("\\|"))).flatMap(
            m => {
                val arrayBuffer = new ArrayBuffer[(Long, (String, String))]()
                for (elem <- m._3) {
                    arrayBuffer.+=((m._1, (m._2, elem)))
                }
                arrayBuffer
            }
        )

        /**
         *  input: ratingRdd
         *  -> map -> (movieId, rating)
         */
        val movieIdRatingRdd = ratingRdd.map(m => (m.movieId, m.rating))


        /**
         *  input:
         *  -> join: movieId -> (movieId, ((title, genre), rating))
         *  -> map -> ((movieId, title, genre), rating))
         *  -> groupByKey: (movieId, title, genre) ->  ((movieId, title, genre), List(rating)))
         *  -> mapValues -> ((movieId, title, genre), rating_avg))
         */
        val groupByMovieIdTitleGenreAvgRatingRdd = movieIdTitleGenreRdd.join(movieIdRatingRdd).map(m => ((m._1, m._2._1._1, m._2._1._2), m._2._2)).groupByKey().mapValues(
            m => {
                if (m.size >= 50) {
                    m.sum * 1.0 / m.size
                } else {
                    0d
                }
            }
        )

        /**
         *  input:
         *  -> map -> (movieId, title, genre, rating)
         *  -> groupBy: genre -> (genre, (movieId, title, genre, rating))
         *  -> mapValues: -> (genre, List((title, genre, rating)))
         *  -> flatmap -> (title, genre, rating)
         */
        val result = groupByMovieIdTitleGenreAvgRatingRdd.map(m => (m._1._1, m._1._2, m._1._3, m._2)).groupBy(_._3).mapValues(
            m => {
                val top10 = m.toList.sortBy(_._4).reverse.take(5)
                top10.map(mm => (mm._3, mm._2, mm._4))
            }
        ).flatMap(_._2)
        result.foreach(println)

    }



    override def analysis2(): Unit ={


        //获得所有电影的movieID，name，types      (movieID, (name, types))
        val movieID_name_types: RDD[(String, (String, String))] = movieDao.moviesRdd.map(x => (x._1, (x._2, x._3)))

        //获得所有的movieID，rating (movieID, rating)
        val movieID_rating: RDD[(String, String)] = movieDao.ratingsRdd.map(x => (x._2, x._3))

        //关联movieID_name_types与movieID_rating   (movieID, ((name, types), rating))  ---> (types, name, rating)
        val types_name_rating: RDD[((String, String), Int)] = movieID_name_types.join(movieID_rating)
                .map(x => ((x._2._1._2, x._2._1._1), x._2._2.toInt))

        //((types, name), Iterable(rating))  ---> (types, name, avg)
        val types_name_avg: RDD[(String, String, Double)] = types_name_rating.groupByKey().map(x => {
            var avg = 0d
            if (x._2.size >= 50)
                avg = x._2.sum * 1.0 / x._2.size
            (x._1._1, x._1._2, avg)
        })

        //(types, name, avg)     划分types：将Action|Adventure|Comedy|Sci-Fi拆开
        var tempArray: Array[(String, String, Double)] = Array(("", "", 0d))

        types_name_avg.collect().foreach(x => {
            //Action|Adventure|Comedy|Sci-Fi   ---> Arrays(Action, Adventure, Comedy, Sci-Fi)
            val types: Array[String] = x._1.split("\\|")
            //将所有的types_name_avg中的元素拆分后存于tempArray数组中
            tempArray = types.map((_, x._2, x._3)).union(tempArray)
        })

        //(type, name, avg)  包含所有类型电影的排序
        val type_name_avg = EnvUtil.getSparkContext.makeRDD(tempArray).filter(_._3 > 0).sortBy(x => (x._1, x._3), false)

        //(type, Iterable(type, name, avg))  打印前五
        type_name_avg.groupBy(_._1).sortByKey().foreach(x => {
            var count = 0
            val list: List[(String, String, Double)] = x._2.toList
            while(count < list.size  && count < 5){
                println(list(count))
                count += 1
            }
            println()
        })
    }
}


/*



 */


