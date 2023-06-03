package com.study.spark.base

/**
 * 提交应用
 * bin/spark-submit \
 * -- class org.apache.spark.examples.SparkPi \
 * -- master local[2] \
 * ./examples/jars/spark-examples_2.12-3.0.0.jar \
 * 10
 *
 *  -- class : 表示要执行程序的主类，此处可以更换为咱们自己写的应用程序
 *  -- master : 部署模式，默认为本地模式，数字表示分配的虚拟 CPU 核数量
 *  spark-examples_2.12-3.0.0.jar : 运行的应用类所在的 jar 包，实际使用时，可以设定为咱们自己打的 jar包
 *  10 : 表示程序的入口参数，用于设定当前应用的任务数量
 *
 */
object _02_SparkTest {

}
