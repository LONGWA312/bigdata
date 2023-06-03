package com.study.scala.base

/**
 * 特质（Trait）

        Scala 语言中，采用特质 trait（特征）来代替接口的概念，也就是说，多个类具有相同的特质（特征）时，就可以将这个特质（特征）独立出来，采用关键字 trait 声明。
        Scala 中的 trait 中即可以有抽象属性和方法，也可以有具体的属性和方法，一个类可以混入（mixin）多个特质。这种感觉类似于 Java 中的抽象类。
        Scala 引入 trait 特征，第一可以替代 Java 的接口，第二个也是对单继承机制的一种补充。

 * 特质的声明
        基本语法
            trait 特质名 {
                特质主题
            }


 * 特质基本语法
        一个类具有某种特质（特征），就意味着这个类满足了这个特质（特征）的所有要素，所以在使用时，也采用了 extends 关键字，
        如果有多个特质或存在父类，那么需要采用 with关键字连接。

        基本语法
            没有父类： class 类名 extends 特质 1 with 特质 2 with 特质 3 …
            有父类：  class 类名 extends 父类 with 特质 1 with 特质 2 with 特质 3…

        说明
            （1）类和特质的关系：使用继承的关系。
            （2）当一个类去继承特质时，第一个连接词是 extends，后面是 with。
            （3）如果一个类在同时继承特质和父类时，应当把父类写在 extends 后

        注意：
            （1）特质可以同时拥有抽象方法和具体方法
            （2）一个类可以混入（mixin）多个特质
            （3）所有的 Java 接口都可以当做 Scala 特质使用
            （4）动态混入：可灵活的扩展类的功能
            （4.1）动态混入：创建对象时混入 trait，而无需使类混入该 trait
            （4.2）如果混入的 trait 中有未实现的方法，则需要实现

 * 特质的叠加

    由于一个类可以混入（mixin）多个 trait，且 trait 中可以有具体的属性和方法，若混入的特质中具有相同的方法（方法名，参数列表，返回值均相同），
    必然会出现继承冲突问题。冲突分为以下两种：

    第一种：
        一个类（Sub）混入的两个 trait（TraitA，TraitB）中具有相同的具体方法，且两个 trait 之间没有任何关系，
        解决这类冲突问题，直接在类（Sub）中重写冲突方法。

    第二种：
        一个类（Sub）混入的两个 trait（TraitA，TraitB）中具有相同的具体方法，且两个 trait 继承自相同的 trait（TraitC），
        及所谓的“钻石问题”，解决这类冲突问题，Scala采用了特质叠加的策略。

        所谓的特质叠加，就是将混入的多个 trait 中的冲突方法叠加起来，案例如下，

 * 特质叠加执行顺序
    思考：上述案例中的 super.describe()调用的是父 trait 中的方法吗？
    当一个类混入多个特质的时候，scala 会对所有的特质及其父特质按照一定的顺序进行
    排序，而此案例中的 super.describe()调用的实际上是排好序后的下一个特质中的 describe()方法。

    （1）案例中的 super，不是表示其父特质对象，而是表示上述叠加顺序中的下一个特质，
        即，MyClass 中的 super 指代 Color，Color 中的 super 指代 Category，Category 中的 super指代 Ball。
    （2）如果想要调用某个指定的混入特质中的方法，可以增加约束：super[]，例如super[Category].describe()

 * 特质自身类型
    1）说明
        自身类型可实现依赖注入的功能。

 * 特质和抽象类的区别
    1.优先使用特质。一个类扩展多个特质是很方便的，但却只能扩展一个抽象类。
    2.如果你需要构造函数参数，使用抽象类。因为抽象类可以定义带参数的构造函数，而特质不行（有无参构造）。

 */


/*
    特质的声明
        基本语法
            trait 特质名 {
                特质主题
            }
*/

trait PersonTrait{

    /*
         声明属性
     */
    var name: String = _

    /*
        声明方法
     */
    def eat(): Unit = {

    }

    /*
        抽象属性
     */
    var age: Int


    /*
        抽象方法
     */
    def say(): Unit

}


/*
    （1）特质可以同时拥有抽象方法和具体方法
    （2）一个类可以混入（mixin）多个特质
    （3）所有的 Java 接口都可以当做 Scala 特质使用
    （4）动态混入：可灵活的扩展类的功能
    （4.1）动态混入：创建对象时混入 trait，而无需使类混入该 trait
    （4.2）如果混入的 trait 中有未实现的方法，则需要实现
 */
trait PersonTrait2{

    /*
        （1）特质可以同时拥有抽象方法和具体方法
     */
    //声明属性
    var name: String = _

    //抽象属性
    var age: Int

    //具体方法
    def eat(): Unit = {
        println("eat")
    }

    //抽象方法
    def say(): Unit

}

trait SexTrait {

    var sex: String
}


/*
    （2）一个类可以实现/继承多个特质
    （3）所有的 Java 接口都可以当做 Scala 特质使用
 */
class Teacher13 extends PersonTrait2 with java.io.Serializable{

    override var age: Int = _

    override def say(): Unit = {
        println("say")
    }
}

object TestTrait {

    def main(args: Array[String]): Unit = {

        val teacher = new Teacher13

        teacher.say()

        teacher.eat()

        /*
            动态混入：可灵活的扩展类的功能
         */
        val t2 = new Teacher13 with SexTrait {
            override var sex: String = "男"
        }

        println(t2.sex)
    }

}




/**
 * 特质的叠加

    由于一个类可以混入（mixin）多个 trait，且 trait 中可以有具体的属性和方法，若混入的特质中具有相同的方法（方法名，参数列表，返回值均相同），
    必然会出现继承冲突问题。冲突分为以下两种：

    第一种：
        一个类（Sub）混入的两个 trait（TraitA，TraitB）中具有相同的具体方法，且两个 trait 之间没有任何关系，
        解决这类冲突问题，直接在类（Sub）中重写冲突方法。

    第二种：
        一个类（Sub）混入的两个 trait（TraitA，TraitB）中具有相同的具体方法，且两个 trait 继承自相同的 trait（TraitC），
        及所谓的“钻石问题”，解决这类冲突问题，Scala采用了特质叠加的策略。

        所谓的特质叠加，就是将混入的多个 trait 中的冲突方法叠加起来，案例如下，

 */

trait Ball {

    def describe(): String = {
        "ball"
    }
}

trait Color extends Ball {

    override def describe(): String = {
        "blue-" + super.describe()
    }
}

trait Category extends Ball {

    override def describe(): String = {
        "foot-" + super.describe()
    }
}

class MyBall extends Category with Color{

    override def describe(): String = {

        "my ball is a " + super.describe()
    }
}

object TestTrait3{

    def main(args: Array[String]): Unit = {

        println(new MyBall().describe())

    }
}


/*
    特质自身类型
    1）说明
        自身类型可实现依赖注入的功能。
 */

class User(val name: String, val age: Int)


trait Dao {
    def insert(user: User): Unit = {
        println("insert into database: " + user.name)
    }
}

trait APP {

    /*
        注入自身类型
     */
    _: Dao =>

    def login(user: User): Unit = {
        println("login : " + user.name)
        insert(user)
    }
}

object MyApp extends APP with Dao {

    def main(args: Array[String]): Unit = {
        login(new User("boo", 11))
    }
}