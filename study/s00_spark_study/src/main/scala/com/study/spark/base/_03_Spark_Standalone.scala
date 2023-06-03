package com.study.spark.base

/**
 * Standalone模式
 *
 * local 本地模式毕竟只是用来进行练习演示的，真实工作中还是要将应用提交到对应的集群中去执行，
 * 这里我们来看看只使用 Spark 自身节点运行的集群模式，也就是我们所谓的独立部署（Standalone）模式。
 * Spark 的 Standalone 模式体现了经典的 master-slave 模式。

 * Standalone模式下的应用提交
    bin/spark-submit \
    --class org.apache.spark.examples.SparkPi \
    --master spark://linux1:7077
    ./examples/jars/spark-examples_2.12-3.0.0.jar \
    10

 *  --class : 表示要执行程序的主类，此处可以更换为咱们自己写的应用程序
 *  --master : 部署模式，默认为本地模式，数字表示分配的虚拟 CPU 核数量
 *  spark-examples_2.12-3.0.0.jar : 运行的应用类所在的 jar 包，实际使用时，可以设定为咱们自己打的 jar包
 *  10 : 表示程序的入口参数，用于设定当前应用的任务数量
 *



 * 提交参数说明
    bin/spark-submit \
    --class <main-class> \
    --master <master-url> \
    # other options
    <application-jar> \
    [application-arguments]

 * 参数选项
    --class: Spark 程序中包含主函数的类
    --master: Spark 程序运行的模式(环境)
        模式有：
            local[*]: 本地模式
            spark://linux1:7088: 远程节点模式
            yarn: yarn模式
    --executor-memory 1G: 指定每个excutor可用内存为1G
    --total-executor-cores 2: 指定所有executor使用的cpu核数为2
    --executor-cores: 指定每个executor使用的cpu核数
    application-jar: 打包好的应用 jar，包含依赖。这个 URL 在集群中全局可见。 比如 hdfs:// 共享存储系统，如果是file:// path，那么所有的节点的path 都包含同样的 jar
    application-arguments: 传给main()方法的参数
 *
 */
object _03_Spark_Standalone {

}
