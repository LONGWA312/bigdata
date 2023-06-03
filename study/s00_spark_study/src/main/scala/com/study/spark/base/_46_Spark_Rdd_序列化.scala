package com.study.spark.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/*

RDD序列化

    1. 闭包检查
        从计算的角度, 算子以外的代码都是在 Driver 端执行, 算子里面的代码都是在 Executor端执行。
        那么在 scala 的函数式编程中，就会导致算子内经常会用到算子外的数据，这样就形成了闭包的效果，
        如果使用的算子外的数据无法序列化，就意味着无法传值给 Executor端执行，就会发生错误，
        所以需要在执行任务计算前，检测闭包内的对象是否可以进行序列化，这个操作我们称之为闭包检测。
        Scala2.12 版本后闭包编译方式发生了改变

    2. 序列化方法和属性
        从计算的角度, 算子以外的代码都是在 Driver 端执行, 算子里面的代码都是在 Executor端执行


    3. Kryo 序列化框架
        参考地址: https://github.com/EsotericSoftware/kryo
        Java 的序列化能够序列化任何的类。但是比较重（字节多），序列化后，对象的提交也比较大。
        Spark 出于性能的考虑，Spark2.0 开始支持另外一种 Kryo 序列化机制。
        Kryo 速度是 Serializable 的 10 倍。当 RDD 在 Shuffle 数据的时候，简单数据类型、数组和字符串类型已经在 Spark 内部使用 Kryo 来序列化。
        注意：即使使用 Kryo 序列化，也要继承 Serializable 接口。


 */

class User extends Serializable {
    var age: Int = _
}

object _046_Spark_Rdd_Serializable_Function {

    def main(args: Array[String]): Unit = {


        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
        val sc = new SparkContext(sparkConf)

        val user = new User()

        user.age = 30

        val rdd: RDD[Int] = sc.makeRDD(List[Int](), 2)


        /*
            分布式计算的时候，如果driver端的对象需要在executor端使用的场合，这个对象需要序列化

            什么是Driver端的，什么是excutor端的？
            rdd的方法有特殊含义
            方法外部的代码的执行都是在driver端， 方法内部的逻辑都是在executor端执行的
            所以才会将rdd的方法称之为算子

            匿名函数都存在闭包
            如果存在闭包，就要判断是否用到了外部的变量
            如果用到了，那么就必须要求这个变量的数据类型能够序列化


            case class 样例类
            样例类在编译的时候自动实现可序列化接口

         */
        rdd.foreach(
            num => {
                println(user.age + num)
            }
        )

        sc.stop()
    }


    def main(): Unit = {


        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
        val sc = new SparkContext(sparkConf)



        sc.stop()
    }

    class Search(query:String) {


        def isMatch(s: String): Boolean ={
            s.contains(query)
        }

        /*
            函数序列化的案例
            rdd的算子中使用了Search对象的方法，而search对象没有进行序列化，所以调用会报错
         */
        def getMatch1(rdd: RDD[String]): RDD[String] ={
            rdd.filter(this.isMatch)
        }



        /*
            属性序列化的案例
            scala中构造参数其实也是对象的属性，只不过声明了私有的属性，但是没有提供set,get方法
         */
        def getMatch2(rdd: RDD[String]): RDD[String] = {

            /*
                这样写会报错
                query为构造函数的参数，直接传入会保证错
             */
            rdd.filter(x => x.contains(query))

            /*
                val q = query在driver端执行，然后将q变量传入到算子中执行不会报错
             */
            val q = query
            rdd.filter(x => x.contains(q))

        }
    }

    /*
        Kryo 序列化框架
     */

    def main2(): Unit ={

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
        val sc = new SparkContext(sparkConf)

    }
}
