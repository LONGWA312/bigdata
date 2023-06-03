package com.study.scala.base

/**
 *
    单词计数：将集合中出现的相同的单词，进行计数，取计数排名前三的结果
 */
object _38_Collection_wordcount {

    def main(args: Array[String]): Unit = {

        val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

        /*
             1) 将每一个字符串转换成一个一个单词
         */
        val wordList: List[String] = stringList.flatMap(x => x.split(" "))


        /*
             2) 将相同的单词放置在一起
         */
        val wordToWordMap: Map[String, List[String]] = wordList.groupBy(word => word)

        /*
             3) 对相同的单词进行计数
         */
        val wordToCountMap: Map[String, Int] = wordToWordMap.map(tuple => (tuple._1, tuple._2.size))

        /*
            4）对计数完成后的结果进行排序（降序）
         */
        val sortList: List[(String, Int)] = wordToCountMap.toList.sortWith {
            (left, right) => {
                left._2 > right._2
            }
        }

        /*
             5) 对排序后的结果取前 3 名
         */
        val resultList: List[(String, Int)] = sortList.take(3)

        println(resultList)

    }

}

/**
 * 复杂wordCount
 * 方式一，不通用
 */
object TestWordCount{

    def main(args: Array[String]): Unit = {


        val tupleList = List(("Hello Scala Spark World", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

        val stringList: List[String] = tupleList.map(t => (t._1 + " ") * t._2)
        println(stringList)


        //val words: List[String] = stringList.flatMap(s => s.split(" "))
        val words: List[String] = stringList.flatMap(_.split(" "))
        val groupMap: Map[String, List[String]] = words.groupBy(word => word)
        println(groupMap)

        val wordToCount: Map[String, Int] = groupMap.map(tuple => (tuple._1, tuple._2.size))

        val result: List[(String, Int)] = wordToCount.toList.sortWith((x, y) => {
            x._2 > y._2
        }).take(3)

        println(result)


        /*
            以上步骤可以简化为
         */
        val result2: List[(String, Int)] = tupleList.map(t => (t._1 + " ") * t._2)
                .flatMap(m => m.split(" "))
                .groupBy(word => word)
                .map(t => (t._1, t._2.size))
                .toList.sortWith((left, right) => left._2 > right._2)
                .take(3)
        println(result2)

    }

}

object TestWordCount2{

    def main(args: Array[String]): Unit = {


        val tupleList = List(("Hello Scala Spark World", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

        /*
            (Hello,4),(Scala,4),(Spark,4),(World,4)
            (Hello,3),(Scala,3),(Spark,3)
            (Hello,2),(Scala,2)
            (Hello,1)
         */
        val wordToCountList: List[(String, Int)] = tupleList.flatMap(t => {
            val strings = t._1.split(" ")
            strings.map(word => (word, t._2))
        })

        val stringToTupleMap: Map[String, List[(String, Int)]] = wordToCountList.groupBy(t => t._1)

        val stringToIntsMap: Map[String, List[Int]] = stringToTupleMap.mapValues(datas => datas.map(t => t._2))

        println(stringToIntsMap)

        val result: List[(String, Int)] = stringToIntsMap.map(t => (t._1, t._2.sum)).toList.sortWith((x, y) => x._2 > y._2).take(3)
        println(result)


    }
}
