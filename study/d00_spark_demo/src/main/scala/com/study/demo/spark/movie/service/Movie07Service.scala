package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import org.apache.spark.rdd.RDD

/*

需求：

7、求好片（评分>=4.0）最多的那个年份的最好看的 10 部电影

 */
class Movie07Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val start = System.currentTimeMillis()
        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val movieRdd: RDD[Movie] = movieDao.readMovies()

        /**
         *  input: ratingRdd
         *  -> map -> (movieId, rating)
         */
        val movieIdRatingRdd = ratingRdd.map(m => (m.movieId, m.rating))


        /**
         * input:
         *  -> groupByKey -> (movieId, List(rating))
         *  -> mapValues -> (movieId, rating_avg)
         *  -> filter: rating_avg >= 4.0
         */
        val movieIdRatingAvgRdd = movieIdRatingRdd.groupByKey().mapValues(
            m => {
                if (m.size > 50) {
                    m.sum * 1.0 / m.size
                } else {
                    0d
                }
            }
        )

//        movieIdRatingAvgRdd.cache()
        /**
         *  input:
         *  -> filter: rating_avg >= 4.0
         */
        val filtedMovieIdRatingAvgRdd = movieIdRatingAvgRdd.filter(_._2 >= 4.0)


        /**
         *  input: movieRdd
         *  -> map -> (movieId, movie)
         */
        val movieIdMovieRdd = movieRdd.map(m => (m.movieId, m))


        /**
         *  input:
         *  -> join: by movieId -> (movieId, (rating_avg, movie))
         *  -> map -> (year, 1)
         *  -> reduceByKey -> (year, times)
         *  -> sortBy: times, false
         *  -> first._1 -> year
         */
        val year: String = filtedMovieIdRatingAvgRdd.join(movieIdMovieRdd).map(
            m => {
                val title = m._2._2.title
                val year = title.substring(title.length - 5, title.length - 1)
                (year, 1)
            }
        ).reduceByKey(_ + _).sortBy(_._2, ascending = false).first()._1


        /**
         *  input:
         *  -> filter: title.contains(year)
         */
        val filterByYearMoveRdd = movieIdMovieRdd.filter(f => f._2.title.contains(year))


        /**
         * input:
         * -> join: movieId -> (movieId, (rating_avg, movie))
         * -> map  -> (movieId, title, rating_avg)
         * -> sortBy: rating_avg,
         * -> take(10) -> Array((movieId, title, rating_avg))
         */
        val result = movieIdRatingAvgRdd.join(filterByYearMoveRdd).map(m => (m._1, m._2._2.title, m._2._1)).sortBy(_._3, ascending = false).take(10)
        result.foreach(println)

        val end = System.currentTimeMillis()
        println("Movie07Service execute analysis time: " + {end - start} + "ms")

    }



    override def analysis2(): Unit ={

        //1、找到所有的好片的movieID
        //(movieID, rating) ---> (movieID, Iterable(rating))  ---> (movieID, avg)（avg >= 4.0）
        val movieID_avg :RDD[(String, Double)]= movieDao.ratingsRdd.map(x => (x._2, x._3.toInt)).groupByKey().map(x =>{
            var avg = 0d
            if(x._2.size >= 50)
                avg = x._2.sum * 1.0 / x._2.size
            (x._1, avg)
        }).filter(_._2 >= 4.0)

        //(movieID, (name, year))
        val movieID_name_year: RDD[(String, (String, String))] = movieDao.moviesRdd.map(x => (x._1, (x._2, x._2.substring(x._2.length - 5, x._2.length - 1))))

        //2、找到好片最多的年代
        //关联movieID_avg与movieID_name_year，(movieID, (avg, (name, year)))   --> (year, Iterable(movieID))
        val year_count: (String, Int) = movieID_avg.join(movieID_name_year).map(x => (x._2._2._2, x._1))
                .groupByKey().map(x => (x._1, x._2.size)).sortBy(_._2, false).first()

        //3、找到该年最好看的10部电影
        //(movieID, name) ---> (movieID, (name, avg))  ---> (movieID, name, avg)
        val movieID_name_avg = movieID_name_year.filter(_._2._2 == year_count._1).map( x => (x._1, x._2._1))
                .join(movieID_avg).map(x => (x._1, x._2._1, x._2._2)).sortBy(_._3, false).take(10)

        //输出结果
        movieID_name_avg.foreach(println(_))


    }
}

/*
(2762,Sixth Sense, The (1999),4.406262708418057)
(2858,American Beauty (1999),4.3173862310385065)
(2571,Matrix, The (1999),4.315830115830116)
(3114,Toy Story 2 (1999),4.218927444794953)
(3147,Green Mile, The (1999),4.154664484451718)
(2501,October Sky (1999),4.137755102040816)
(2997,Being John Malkovich (1999),4.125390450691656)
(3083,All About My Mother (Todo Sobre Mi Madre) (1999),4.086294416243655)
(2959,Fight Club (1999),4.077188146106134)
(3006,Insider, The (1999),4.0588235294117645)


(2762,Sixth Sense, The (1999),4.406262708418057)
(2858,American Beauty (1999),4.3173862310385065)
(2571,Matrix, The (1999),4.315830115830116)
(3114,Toy Story 2 (1999),4.218927444794953)
(3147,Green Mile, The (1999),4.154664484451718)
(2501,October Sky (1999),4.137755102040816)
(2997,Being John Malkovich (1999),4.125390450691656)
(3083,All About My Mother (Todo Sobre Mi Madre) (1999),4.086294416243655)
(2959,Fight Club (1999),4.077188146106134)
(3006,Insider, The (1999),4.0588235294117645)


 */

