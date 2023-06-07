package com.study.spark.spark_sql.base

import com.study.spark.spark_sql.bean.Movie
import org.apache.spark.sql.functions.{collect_list, explode, grouping}
import org.apache.spark.sql.types.{IntegerType, LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession, types}

import java.util

object SparkSql_行列转换 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder()
                .master("local[*]")
                .appName("function_array_operation")
                .getOrCreate()

        import spark.implicits._
        val movies = spark.read.json("s00_spark_study/data/input/movies.json").as[Movie].toDF()
        movies.show()
        /*
        +--------------------+-------+--------------------+
        |              genres|movieId|               title|
        +--------------------+-------+--------------------+
        |[Animation, Child...|      1|    Toy Story (1995)|
        |[Adventure, Child...|      2|      Jumanji (1995)|
        |   [Comedy, Romance]|      3|Grumpier Old Men ...|
        |     [Comedy, Drama]|      4|Waiting to Exhale...|
        |            [Comedy]|      5|Father of the Bri...|
        +--------------------+-------+--------------------+
         */
        column_to_line(spark, movies)



        val structType = types.StructType(
            Array(
                StructField("item", StringType, nullable = false),
                StructField("name", StringType, nullable = false),
                StructField("score", IntegerType, nullable = false)
            )
        )
        val dataList = new util.ArrayList[Row]()
        dataList.add(Row("数学", "张三", 88))
        dataList.add(Row("英语", "张三", 77))
        dataList.add(Row("语文", "张三", 92))
        dataList.add(Row("数学", "王五", 65))
        dataList.add(Row("语文", "王五", 87))
        dataList.add(Row("英语", "王五", 90))
        dataList.add(Row("数学", "李雷", 67))
        dataList.add(Row("语文", "李雷", 33))
        dataList.add(Row("英语", "李雷", 24))
        dataList.add(Row("数学", "宫九", 77))
        dataList.add(Row("语文", "宫九", 87))
        dataList.add(Row("英语", "宫九", 90))

        val dataFrame = spark.createDataFrame(dataList, structType).toDF()
        dataFrame.show(false)
        /*
        +----+----+-----+
        |item|name|score|
        +----+----+-----+
        |数学|张三|88   |
        |英语|张三|77   |
        |语文|张三|92   |
        |数学|王五|65   |
        |语文|王五|87   |
        |英语|王五|90   |
        |数学|李雷|67   |
        |语文|李雷|33   |
        |英语|李雷|24   |
        |数学|宫九|77   |
        |语文|宫九|87   |
        |英语|宫九|90   |
        +----+----+-----+
         */
        line_to_column(spark, dataFrame)




    }


    /**
     * 列转行
     * 将movies中的数据按genres进行列转行, 新行的字段为genre
     * 使用 dataFrame的 withColumn 函数
     * 使用functions.explode(函数)
     */
    private def column_to_line(spark: SparkSession, movies: DataFrame): Unit = {
        import spark.implicits._

        /**
         * 先用withColumn()和functions.explode()函数，新增拆分后的字段genre
         */
        movies.withColumn("genre", explode($"genres")).show(5, truncate = false)
        /*
        +--------------------------------+-------+----------------+----------+
        |genres                          |movieId|title           |genre     |
        +--------------------------------+-------+----------------+----------+
        |[Animation, Children's, Comedy] |1      |Toy Story (1995)|Animation |
        |[Animation, Children's, Comedy] |1      |Toy Story (1995)|Children's|
        |[Animation, Children's, Comedy] |1      |Toy Story (1995)|Comedy    |
        |[Adventure, Children's, Fantasy]|2      |Jumanji (1995)  |Adventure |
        |[Adventure, Children's, Fantasy]|2      |Jumanji (1995)  |Children's|
        +--------------------------------+-------+----------------+----------+
         */


        /**
         * 再使用drop()函数，删除原来的字段
         */
        movies.withColumn("genre", explode($"genres")).drop("genres").show(5, truncate = false)
        /*
        +-------+----------------+----------+
        |movieId|title           |genre     |
        +-------+----------------+----------+
        |1      |Toy Story (1995)|Animation |
        |1      |Toy Story (1995)|Children's|
        |1      |Toy Story (1995)|Comedy    |
        |2      |Jumanji (1995)  |Adventure |
        |2      |Jumanji (1995)  |Children's|
        +-------+----------------+----------+
         */


        /**
         * 也可以使用select(), 过滤掉拆分前的字段
         */
        movies.withColumn("genre", explode($"genres")).select("movieId", "title", "genre").show(5, truncate = false)
        /*
        +-------+----------------+----------+
        |movieId|title           |genre     |
        +-------+----------------+----------+
        |1      |Toy Story (1995)|Animation |
        |1      |Toy Story (1995)|Children's|
        |1      |Toy Story (1995)|Comedy    |
        |2      |Jumanji (1995)  |Adventure |
        |2      |Jumanji (1995)  |Children's|
        +-------+----------------+----------+
         */
    }


    /**
     * 行转列
     */
    def line_to_column(spark: SparkSession, dataFrame: DataFrame): Unit = {

        /**
         * 按name分组，然后将分数转为列表
         */
        dataFrame
                .groupBy("name")
                .agg(
                    collect_list("score").as("scores"),
                    collect_list("item").as("items")
                )
                .show(false)
        /*
        +----+------------+------------------+
        |name|scores      |items             |
        +----+------------+------------------+
        |王五|[65, 87, 90]|[数学, 语文, 英语]|
        |李雷|[67, 33, 24]|[数学, 语文, 英语]|
        |宫九|[77, 87, 90]|[数学, 语文, 英语]|
        |张三|[88, 77, 92]|[数学, 英语, 语文]|
        +----+------------+------------------+
         */


    }



}
