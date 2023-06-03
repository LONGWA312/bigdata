package com.study.scala.base

/**
 * 泛型
 * 协变和逆变

    1）语法
        class MyList[+T]{ //协变
        }
        class MyList[-T]{ //逆变
        }
        class MyList[T] //不变
    2）说明
        协变：Son 是 Father 的子类，则 MyList[Son] 也作为 MyList[Father]的“子类”。
        逆变：Son 是 Father 的子类，则 MyList[Son]作为 MyList[Father]的“父类”。
        不变：Son 是 Father 的子类，则 MyList[Father]与 MyList[Son]“无父子关系”。
 */



//class MyList1<T>{}
//不变
class MyList2[T]{}
//协变
class MyList3[+T]{}
//逆变
class MyList4[-T]{}


class Parent{}
class Child extends Parent{}
class SubChild extends Child{}


object Scala_TestGeneric {
    def main(args: Array[String]): Unit = {
//        var s:MyList4[Child] = new MyList4[SubChild]
    }
}
