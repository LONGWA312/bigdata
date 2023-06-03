package com.study.demo.spark.movie.service

import com.study.demo.common.BaseService
import com.study.demo.spark.movie.bean.{Movie, Rating, User}
import com.study.demo.spark.movie.dao.MovieDao
import com.study.demo.utils.EnvUtil
import org.apache.spark.rdd.RDD

/*

需求：

8、求 1997 年上映的电影中，评分最高的 10 部 Comedy 类电影

 */
class Movie08Service extends BaseService{


    private val movieDao = new MovieDao()

    override def analysis(): Any = {

        val ratingRdd: RDD[Rating] = movieDao.readRatings()
        val movieRdd: RDD[Movie] = movieDao.readMovies()

        /**
         *  input: movieRdd
         *  -> filter: title.contains("1997") && genres.contains("Comedy")
         *  -> map -> (movieId, (title, genres))
         */
        val movieIdMovieRdd = movieRdd.filter(f => f.title.contains("1997") && f.genres.contains("Comedy")).map(m => (m.movieId, (m.title, m.genres)))


        /**
         *  input: ratingRdd
         *  -> map -> (movieId, rating)
         */
        val movieIdRatingRdd = ratingRdd.map(m => (m.movieId, m.rating))

        /**
         *  input:
         *  -> join: by movieId -> (movieId, (rating, (title, genres))
         *  -> map -> ((movieId, title, genres), rating))
         *  -> groupByKey: movieId -> ((movieId, title, genres), List(rating))
         *  -> mapValues -> ((movieId, title, genres), rating_avg)
         */
        val movieTypeRatingAvgRdd = movieIdRatingRdd.join(movieIdMovieRdd).map(m => ((m._1, m._2._2._1, m._2._2._2), m._2._1)).groupByKey().mapValues(
            m => {
                if (m.size > 50) {
                    m.sum * 1.0 / m.size
                } else {
                    0d
                }
            }
        )

        /**
         *  input:
         *  -> sortBy: rating_avg
         *  -> take(10) -> ((movieId, title, genres), rating_avg)
         *  -> map -> (movieId, title, genres, rating_avg)
         */
        val result = movieTypeRatingAvgRdd.sortBy(_._2, ascending = false).take(10).map(m => (m._1._1, m._1._2, m._1._3, m._2))
        result.foreach(println)


    }



    def analysis2(): Unit ={

        //(movieID, (name, year, type))
        val movieID_name_year_type: RDD[(String, (String, String, String))] = movieDao.moviesRdd
                .map(x => (x._1, (x._2, x._2.substring(x._2.length - 5, x._2.length - 1), x._3)))

        //找到所有1997年的comedy类型的电影 (movieID, (name, 1997, comedy))
        val movieID_name_1997_comedy: RDD[(String, (String, String, String))] = movieID_name_year_type.filter(x => {x._2._2 == "1997" && x._2._3.toLowerCase.contains("comedy")} )

        //(movieID, (rating, (name, 1997, comedy)))  ---> (movieID, (name, comedy, rating))
        val movieID_name_comedy_rating: RDD[(String, (String, String, String))] = movieDao.ratingsRdd.map(x => (x._2, x._3))
                .join(movieID_name_1997_comedy).map(x => (x._1, (x._2._2._1, x._2._2._3, x._2._1)))


        //(movieID, Iterable(rating))  ---> (movieID, avg)
        val movieID_avg: Array[(String, Double)] = movieID_name_comedy_rating.map(x => (x._1, x._2._3.toInt))
                .groupByKey().map(x => {
            var avg = 0d
            if (x._2.size >= 50)
                avg = x._2.sum * 1.0 / x._2.size
            (x._1, avg)
        }).distinct().sortBy(_._2, false).take(10)

        //(movieID, (avg, (name, comedy, rating)))  ---> (movieID, name, comedy, avg)
        val movieID_name_comedy_avg: RDD[(String, String, String, Double)] = EnvUtil.getSparkContext.makeRDD(movieID_avg)
                .join(movieID_name_comedy_rating).map(x => (x._1, x._2._2._1, x._2._2._2, x._2._1)).distinct().sortBy(_._4, false)

        //输出结果
        movieID_name_comedy_avg.foreach(println(_))

    }
}


/*

(2324,Life Is Beautiful (La Vita � bella) (1997),Comedy|Drama,4.329861111111111)
(1827,Big One, The (1997),Comedy|Documentary,4.0)
(1784,As Good As It Gets (1997),Comedy|Drama,3.9501404494382024)
(1641,Full Monty, The (1997),Comedy,3.872393661384487)
(1734,My Life in Pink (Ma vie en rose) (1997),Comedy|Drama,3.825870646766169)
(1500,Grosse Pointe Blank (1997),Comedy|Crime,3.813380281690141)
(1580,Men in Black (1997),Action|Adventure|Comedy|Sci-Fi,3.739952718676123)
(1517,Austin Powers: International Man of Mystery (1997),Comedy,3.7103734439834026)
(2029,Billy's Hollywood Screen Kiss (1997),Comedy|Romance,3.6710526315789473)
(1485,Liar Liar (1997),Comedy,3.5)


(2324,Life Is Beautiful (La Vita � bella) (1997),Comedy|Drama,4.329861111111111)
(1827,Big One, The (1997),Comedy|Documentary,4.0)
(1784,As Good As It Gets (1997),Comedy|Drama,3.9501404494382024)
(1641,Full Monty, The (1997),Comedy,3.872393661384487)
(1734,My Life in Pink (Ma vie en rose) (1997),Comedy|Drama,3.825870646766169)
(1500,Grosse Pointe Blank (1997),Comedy|Crime,3.813380281690141)
(1580,Men in Black (1997),Action|Adventure|Comedy|Sci-Fi,3.739952718676123)
(1517,Austin Powers: International Man of Mystery (1997),Comedy,3.7103734439834026)
(2029,Billy's Hollywood Screen Kiss (1997),Comedy|Romance,3.6710526315789473)
(1485,Liar Liar (1997),Comedy,3.5)


 */


