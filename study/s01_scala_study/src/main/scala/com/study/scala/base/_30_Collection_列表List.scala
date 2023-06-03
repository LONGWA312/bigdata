package com.study.scala.base

/**

不可变 List
    1）说明
        （1）List 默认为不可变集合
        （2）创建一个 List（数据有顺序，可重复）
        （3）遍历 List
        （4）List 增加数据
        （5）集合间合并：将一个整体拆成一个一个的个体，称为扁平化
        （6）取指定数据
        （7）空集合 Nil
 */
object TestList {

    def main(args: Array[String]): Unit = {

        /*
            List默认为不可变集合
            创建一个List(数据有序，可重复)
         */
        val list: List[Int] = List(1, 2, 3, 4, 3)
        println(list.mkString(","))

        /*
            空集合Nil
         */
        val list9 = 1::2::3::4::Nil
        println(list9.mkString(","))

        /*
            List增加数据
            ::的运算规则从右向左
         */
        val list1 = 5::list
        println(list1.mkString(","))

        val list2 = 7::6::5::list
        println(list2.mkString(","))


        /*
            添加到第一个元素位置
         */
        val list3 = list.+:(5)
        println(list3.mkString(","))


        /*
            集合间合并：将一个整体拆成一个一个的个体，称为扁平化
         */
        val list4 = List(8, 9)
        val list5 = list4::list1
        println(list5.mkString(","))

        val list6 = list4 ::: list1
        println(list6.mkString(","))


        /*
            取指定数据
         */
        println(list(0))

        /*
            遍历List
         */
        list.foreach(println)
    }
}


/**

可变 ListBuffer
    1）说明
        （1）创建一个可变集合 ListBuffer
        （2）向集合中添加数据
        （3）打印集合数据
 */

import scala.collection.mutable.ListBuffer
object TestListBuffer{


    def main(args: Array[String]): Unit = {

        /*
            创建一个可变集合
         */
        val bufferList = ListBuffer(1, 2, 3, 4)

        /*
            向集合中添加数据
         */
        bufferList.+=(5)
        bufferList.append(6)
        bufferList.insert(1, 2)


        /*
            打印集合数据
         */
        bufferList.foreach(println)


        /*
            修改集合数据
         */
        bufferList(1) = 6
        bufferList.update(1, 7)


        /*
            删除集合数据
         */
        println(bufferList.mkString(","))
        bufferList.-(5)
        bufferList.-=(5)
        bufferList.remove(5)
        println(bufferList.mkString(","))

    }
}
