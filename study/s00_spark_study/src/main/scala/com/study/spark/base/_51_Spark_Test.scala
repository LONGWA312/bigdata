package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


case class UserVisitAction(
                                  date: String,//用户点击行为的日期
                                  user_id: Long,//用户的 ID
                                  session_id: String,//Session 的 ID
                                  page_id: Long,//某个页面的 ID
                                  action_time: String,//动作的时间点
                                  search_keyword: String,//用户搜索的关键词
                                  click_category_id: Long,//某一个商品品类的 ID
                                  click_product_id: Long,//某一个商品的 ID
                                  order_category_ids: String,//一次订单中所有品类的 ID 集合
                                  order_product_ids: String,//一次订单中所有商品的 ID 集合
                                  pay_category_ids: String,//一次支付中所有品类的 ID 集合
                                  pay_product_ids: String,//一次支付中所有商品的 ID 集合
                                  city_id: Long//城市 i
                          )

object _51_Spark_Test {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setAppName("local[*]").setAppName("Spark_test")
        val sc = new SparkContext(sparkConf)


        val actionRdd: RDD[String] = sc.textFile("s00_spark_study/data/input/user_visit_action.txt")




        /*
            Top10 热门品类
         */
        val clickActionRDD: RDD[String] = actionRdd.filter(action => {
            val datas = action.split("_")
            datas(6) != "-1"
        })

        val clickMapRdd: RDD[(String, Int)] = clickActionRDD.map(
            clickAction => {
                val datas: Array[String] = clickAction.split("_")
                (datas(6), 1)
            }
        )

        val clickCountRdd: RDD[(String, Int)] = clickMapRdd.reduceByKey(_ + _)







    }

}
