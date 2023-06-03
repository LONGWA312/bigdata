package com.study.scala.base

/**
 * 扩展
 * 类型检查和转换
 *  1）说明
        （1）obj.isInstanceOf[T]：判断 obj 是不是 T 类型。
        （2）obj.asInstanceOf[T]：将 obj 强转成 T 类型。
        （3）classOf 获取对象的类名。
 */
class Person14 {

}


object Person14 {

    def main(args: Array[String]): Unit = {

        val person = new Person14

        /*
            obj.isInstanceOf[T]：判断 obj 是不是 T 类型。
         */
        val bool = person.isInstanceOf[Person14]


        /*
            obj.asInstanceOf[T]：将 obj 强转成 T 类型。
         */
        if (bool) {
            val p1 = person.asInstanceOf[Person14]

            println(p1)
        }

        /*
            获取类的信息
         */
        val pClass = classOf[Person14]
        println(pClass)
    }
}


