package com.study.scala.base

/**
 * 常量匹配

    Scala 中，模式匹配可以匹配所有的字面量，包括字符串，字符，数字，布尔值等等。
 */
object TestMatchVal {

    def main(args: Array[String]): Unit = {


        println(describe(5))

    }

    def describe(x: Any) = x match {
        case 5 => "Int five"
        case "hello" => "String hello"
        case true => "Boolean True"
        case '+' => "Char +"
        case _ => "Other"
    }
}


/**
 * 匹配类型
    需要进行类型判断时，可以使用前文所学的 isInstanceOf[T]和 asInstanceOf[T]，也可使用模式匹配实现同样的功能。
 *
 */

object TestMatchClass {

    def describe(x: Any) = x match {
        case i: Int => "Int"
        case s: String => "String"
        case m: List[_] => "List"
        case c: Array[Int] => "Array[Int]"
        case someThing => "Something else" + someThing
    }


    def main(args: Array[String]): Unit = {

        println(describe(List(1, 2, 3, 4)))

        println(describe(Array(1, 2, 3, 4)))
        println(describe(Array("a", "b")))

    }
}



/**
 * 匹配数组
    1）说明
        scala 模式匹配可以对集合进行精确的匹配，例如匹配只有两个元素的、且第一个元素
 */
object TestMatchArray{


    def main(args: Array[String]): Unit = {

        for (arr <- Array(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array(1, 1, 0, 1), Array("hello", 90))){


            val result = arr match {
                /*
                    匹配 Array(0) 这个数组
                 */
                case Array(0) => "0"
                /*
                    匹配有两个元素的数组，然后将将元素值赋给对应的 x,y
                 */
                case Array(x, y) => x + "," + y
                /*
                    匹配以 0 开头和数组
                 */
                case Array(0, _*) => "以 0 开头的数组"

                case _ => "somthing else"
            }

            println("result = " + result)
        }

    }
}


/**
 *
    匹配列表
 */
object TestMatchList{

    /**
     * 方式一
     */
    def main(): Unit = {

        /*
            list 是一个存放 List 集合的数组
         */
        for (list <- Array(List(0), List(1, 0), List(0, 0, 0), List(1, 0, 0), List(88))){

            val result: String = list match {
                case List(0) => "0"
                case List(x, y) => x + "," + y
                case List(0, _*) => "0 ..."
                case _ => "something else ..."
            }
            println(result)
        }
    }


    /**
     * 方式二
     */
    def main2(): Unit = {

        val list: List[Int] = List(1, 2, 3, 4)
        list match {
            case first :: second :: rest => println(first + "-" + second + "-" + rest)
            case _ => println("something else...")
        }

    }

    def main(args: Array[String]): Unit = {

        main()
        main2()
    }
}


/**
 * 匹配元组

 */
object TestMatchTuple {

    def main(args: Array[String]): Unit = {

        for (tuple <- Array((0, 1), (1, 0), (1, 1), (1, 0, 2))) {
            val result: String = tuple match {
                /*
                    第一个元素是0的元组
                */
                case (0, _) => "0 ..."
                /*
                    匹配后一个元素是 0 的对偶元组
                 */
                case (y, 0) => "" + y + "0"
                case (a, b) => "" + a + " " + b
                case _ => "something else..."
            }
            println(result)
        }
    }
}


/**
 * 匹配扩展，特殊匹配
 */
object TestGeneric{

    def main(args: Array[String]): Unit = {

        /*
            特殊模式匹配一： 打印元组的第一个元素
         */
        for(elem <- Array(("a", 1), ("b", 2),("c", 3))){
            println(elem._1)
        }

        for((word, count) <- Array(("a", 1), ("b", 2),("c", 3))){
            println(word)
        }

        for(("a", count) <- Array(("a", 1), ("b", 2),("c", 3))){
            println(count)
        }


        /*
            特殊的模式匹配二： 给元组元素命名
         */
        var (id, name, age): (Int, String , Int) = (100, "zs", 20)
        println((id, name, age))


        /*
            特殊的模式匹配三： 遍历集合中的元组，给 count * 2
         */
        val list: List[(String, Int)] = List(("a", 1), ("b", 2), ("c", 3))
        println(list.map(x => (x._1, x._2*2)))

        println(
            list.map{
                case (word, count) => (word, count * 2)
            }
        )

        var list1 = List(("a", ("a", 1)), ("b", ("b", 2)), ("c", ("c", 3)))
        println(
            list1.map{
                case (groupkey,(word,count))=>(word,count*2)
            }
        )
    }
}


/**
 * 匹配对象及样例类

 * val user = User("zhangsan",11)，该语句在执行时，实际调用的是 User 伴生对象中的apply 方法，因此不用 new 关键字就能构造出相应的对象。

 * 当将 User("zhangsan", 11)写在 case 后时[case User("zhangsan", 11) => "yes"]，会默认调用 unapply 方法(对象提取器)，
   user 作为 unapply 方法的参数，unapply 方法将 user 对象的 name 和 age 属性提取出来，与 User("zhangsan", 11)中的属性值进行匹配

 * case 中对象的 unapply 方法(提取器)返回 Some，且所有属性均一致，才算匹配成功,属性不一致，或返回 None，则匹配失败。

 *
    若只提取对象的一个属性，则提取器为 unapply(obj:Obj):Option[T]
    若提取对象的多个属性，则提取器为 unapply(obj:Obj):Option[(T1,T2,T3…)]
    若提取对象的可变个属性，则提取器为 unapplySeq(obj:Obj):Option[Seq[T]]


 *
 *
 */
class User43(val name: String, val age: Int)

object User43{

    def apply(name: String, age: Int): User43 = new User43(name, age)

    def unapply(user: User): Option[(String, Int)] = {
        if (user == null){
            None
        } else {
            Some(user.name, user.age)
        }
    }
}

object TestMatchUnapply{

    def main(args: Array[String]): Unit = {

        val user: User43 = User43("zhangsan", 11)

        val result: String = user match {
            case User43("zhangsan", 11) => "yes"
            case _ => "no"
        }

        println(result)

    }
}


/**
 * 样例类

 （1）语法：
    case class Person (name: String, age: Int)
（2）说明
    ○1 样例类仍然是类，和普通类相比，只是其自动生成了伴生对象，并且伴生对象中自动提供了一些常用的方法，如 apply、unapply、toString、equals、hashCode 和 copy。
    ○2 样例类是为模式匹配而优化的类，因为其默认提供了 unapply 方法，因此，样例类可以直接使用模式匹配，而无需自己实现 unapply 方法。
    ○3 构造器中的每一个参数都成为 val，除非它被显式地声明为 var（不建议这样做）

 */
case class User2(val name: String, val age: Int)

object TestMatchUnapply2{

    def main(args: Array[String]): Unit = {

        val user: User2 = User2("zhangsan", 20)
        val result: String = user match {
            case User2("zhangsan", 20) => "yes"
            case _ => "no"
        }
        println(result)


    }
}


/**

变量声明中的模式匹配

 */
case class Person43(val name: String, val age: Int)

object TestMatchVariable{

    def main(args: Array[String]): Unit = {

        val (x, y) = (1, 2)
        println(s"x=$x, y=$y")


        val Array(first, second, _*) = Array(1, 7, 2, 9)
        println(s"first=$first, second=$second")


        val Person43(name, age) = Person43("zhangsan", 23)
        println(s"name=$name, age=$age")

    }
}




/**
    for表达式中的模式匹配
 */
object TestMatchFor{

    def main(args: Array[String]): Unit = {

        val map = Map("A" -> 1, "B" -> 2, "C" -> 3)

        /*
            直接将map中的key遍历出来
         */
        for((k, v) <- map) {
            println(k + " -> " + v)
        }
        println("-----------------")

        /*
            遍历 value=0 的 k-v ,如果 v 不是 0,过滤
         */
        for((k, 1) <- map) {
            println(k + " -> " + 1)
        }
        println("-----------------")
        for((k, v) <- map if v == 1) {
            println(k + " -> " + 1)
        }
        println("-----------------")

        /*
            if v == 0 是一个过滤的条件
         */
        for((k, v) <- map if v > 1){
            println(k + " -> " + v)
        }
        println("-----------------")

    }

}


/**
 * 偏函数中的匹配模式
 *
    偏函数也是函数的一种，通过偏函数我们可以方便的对输入参数做更精确的检查。例如该偏函数的输入类型为 List[Int]，
    而我们需要的是第一个元素是 0 的集合，这就是通过模式匹配实现的。

 * 偏函数的定义
    val second: PartialFunction[List[Int], Option[Int]] = {
         case x :: y :: _ => Some(y)
    }
    second: 函数名
    PartialFunction：偏函数类型
    List[Int]：参数类型
    Option[Int]: 返回值类型
    case x :: y :: _ => Some(y)： case语句
    注： 该偏函数的功能是返回输入的 List 集合的第二个元素

 * 偏函数原理
    上述代码会被 scala 编译器翻译成以下代码，与普通函数相比，只是多了一个用于参数检查的函数——isDefinedAt，其返回值类型为 Boolean。
    val second = new PartialFunction[List[Int], Option[Int]] {
         //检查输入参数是否合格
         override def isDefinedAt(list: List[Int]): Boolean = list match {
            case x :: y :: _ => true
            case _ => false
         }
         //执行函数逻辑
         override def apply(list: List[Int]): Option[Int] = list match {
            case x :: y :: _ => Some(y)
         }
    }


 * 偏函数的使用
    偏函数不能像 second(List(1,2,3))这样直接使用，因为这样会直接调用 apply 方法，而应该调用 applyOrElse 方法，如下：
    second.applyOrElse(List(1,2,3), (_: List[Int]) => None)
    applyOrElse 方法的逻辑为 if (ifDefinedAt(list)) apply(list) else default。
    如果输入参数满足条件，即 isDefinedAt 返回 true，则执行 apply 方法，否则执行 defalut 方法，default 方法为参数不满足要求的处理逻辑。

 */

/**
 * 将该 List(1,2,3,4,5,6,"test")中的 Int 类型的元素加一，并去掉字符串。
 */
object TestPartialFunction {

    def main(args: Array[String]): Unit = {

        val list = List(1,2,3,4,5,6,"test")

        val list1 = list.map { a => {
            a match {
                case i: Int => i + 1
                case s: String => s + 1
            }
        }
        }

        println(list1.filter(_.isInstanceOf[Int]))


        /*
            方法二
         */
        list.filter(_.isInstanceOf[Int]).map(_.asInstanceOf[Int] + 1).foreach(println)

        /*
            方法三
         */
        list.collect{case x: Int => x + 1}.foreach(println)

    }
}











