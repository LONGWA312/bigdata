package com.study.demo.utils

import com.alibaba.druid.pool.DruidDataSourceFactory

import java.sql.{Connection, PreparedStatement, ResultSet}
import java.util.Properties
import javax.sql.DataSource

object JDBCUtil {


    var dataSource: DataSource = init()

    /*
        初始化连接池方法
     */
    def init(): DataSource = {
        val properties = new Properties()
        val config: Properties = PropertiesUtil.load("config.properties")
        properties.setProperty("driverClassName", "com.mysql.jdbc.Driver")
        properties.setProperty("url", config.getProperty("jdbc.url"))
        properties.setProperty("username", config.getProperty("jdbc.user"))
        properties.setProperty("password", config.getProperty("jdbc.password"))
        properties.setProperty("maxActive", config.getProperty("jdbc.datasource.size"))
        DruidDataSourceFactory.createDataSource(properties)
    }


    /**
     * 获取Mysql连接
     * @return
     */
    def getConnection(): Connection ={
        dataSource.getConnection
    }


    /**
     * 执行sql语句，单条记录插入
     * @param connection ..
     * @param sql ..
     * @param params ..
     * @return
     */
    def executeUpdate(connection: Connection, sql: String, params: Array[Any]): Int = {

        var rtn = 0
        var preparedStatement: PreparedStatement = null
        try {

            connection.setAutoCommit(false)
            preparedStatement = connection.prepareStatement(sql)
            if (params != null && params.length > 0) {
                for (i <- params.indices) {
                    preparedStatement.setObject(i+1, params(i))
                }
            }
            rtn = preparedStatement.executeUpdate()
            connection.commit()
            preparedStatement.close()
        } catch {
            case e: Exception => e.printStackTrace()
        }
        rtn
    }


    /**
     * 执行sql语句， 批量插入数据
     * @param connection ..
     * @param sql ..
     * @param paramsList ..
     * @return
     */
    def executeBatchUpdate(connection: Connection, sql: String, paramsList: Iterable[Array[Any]]): Array[Int] = {

        var rtn: Array[Int] = null
        var preparedStatement: PreparedStatement = null

        try {
            connection.setAutoCommit(false)
            preparedStatement = connection.prepareStatement(sql)
            for (params <- paramsList) {
                if (params != null && params.length > 0) {
                    for (i <- params.indices) {
                        preparedStatement.setObject(1+i, params(i))
                    }
                }
                preparedStatement.addBatch()
            }
            rtn = preparedStatement.executeBatch()
            connection.commit()
            preparedStatement.close()

        } catch {
            case e: Exception => e.printStackTrace()
        }
        rtn
    }


    /**
     * 判断一条数据是否存在
     * @param connection ..
     * @param sql ..
     * @param params ..
     * @return
     */
    def isExist(connection: Connection, sql: String, params: Array[Any]): Boolean = {

        var flag: Boolean = false
        var preparedStatement: PreparedStatement = null

        try {
            preparedStatement = connection.prepareStatement(sql)
            if (params != null && params.length > 0) {
                for (i <- params.indices) {
                    preparedStatement.setObject(1+i, params(i))
                }
            }
            flag = preparedStatement.executeQuery().next()
            preparedStatement.close()

        } catch {
            case e: Exception => e.printStackTrace()
        }
        flag
    }


    /**
     * 获取 MySQL 的一条数据
     * @param connection ..
     * @param sql ..
     * @param params ..
     */
    def getDataFromMysql(connection: Connection, sql: String, params: Array[Any]): Long ={

        var result = 0L
        var preparedStatement: PreparedStatement = null

        try {
            preparedStatement = connection.prepareStatement(sql)
            if (params != null && params.length > 0) {
                for (i <- params.indices) {
                    preparedStatement.setObject(1+0, params(i))
                }
            }
            val resultSet: ResultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                result = resultSet.getLong(1)
            }
            resultSet.close()
            preparedStatement.close()

        } catch {
            case e: Exception => e.printStackTrace()
        }
        result
    }


    /**
     * main方法测试以上代码
     * @param args
     */
    def main(args: Array[String]): Unit = {

    }

}
