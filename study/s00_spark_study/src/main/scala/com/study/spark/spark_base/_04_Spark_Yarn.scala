package com.study.spark.base

/**
 * Yarn模式
    独立部署（Standalone）模式由 Spark 自身提供计算资源，无需其他框架提供资源。
    这种方式降低了和其他第三方资源框架的耦合性，独立性非常强。但是你也要记住，Spark 主要是计算框架，而不是资源调度框架，
    所以本身提供的资源调度并不是它的强项，所以还是和其他专业的资源调度框架集成会更靠谱一些。
    所以接下来我们来学习在强大的 Yarn 环境下 Spark 是如何工作的（其实是因为在国内工作中，Yarn 使用的非常多）。

 * Yarn模式下应用提交
    bin/spark-submit \
    --class org.apache.spark.examples.SparkPi \
    --master yarn \
    --deploy-mode client \
    ./examples/jars/spark-examples_2.12-3.0.0.jar \
    10


 * Spark常用的端口号

    Spark 查看当前 Spark-shell 运行任务情况端口号：4040（计算）
    Spark Master 内部通信服务端口号：7077
    Standalone 模式下，Spark Master Web 端口号：8080（资源）
    Spark 历史服务器端口号：18080
    Hadoop YARN 任务运行情况查看端口号：8088
 */
object _04_Spark_Yarn {

}
