package com.study.demo.spark_sql.movie.bean

/**
 * 描述用户信息的样例类
 * @param userId    用户ID
 * @param gender    性别： F = 女， M = 男
 * @param age   年龄
 * @param occupation    职业
 * @param zipCode   邮政编码
 */
case class User(
                 userId: Long,
                 gender: String,
                 age: Long,
                 occupation: String,
                 zipCode: String
               )
