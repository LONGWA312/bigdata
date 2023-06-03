package com.study.scala.base

/**
 *
 *  集合
        1）Scala 的集合有三大类：序列 Seq、集 Set、映射 Map，所有的集合都扩展自 Iterable特质。
        2）对于几乎所有的集合类，Scala 都同时提供了可变和不可变的版本，分别位于以下两个包：
            不可变集合：scala.collection.immutable
            可变集合： scala.collection.mutable
        3）Scala 不可变集合，就是指该集合对象不可修改，每次修改就会返回一个新对象，而不会对原对象进行修改。类似于 java 中的 String 对象
        4）可变集合，就是这个集合可以直接对原对象进行修改，而不会返回新的对象。类似于 java 中 StringBuilder 对象
        建议：在操作集合的时候，不可变用符号，可变用方法

 */


/**
 *
 *  不可变集合
        1）Set、Map 是 Java 中也有的集合
        2）Seq 是 Java 没有的，我们发现 List 归属到 Seq 了，因此这里的 List 就和 Java 不是同一个概念了
        3）我们前面的 for 循环有一个 1 to 3，就是 IndexedSeq 下的 Range
        4）String 也是属于 IndexedSeq
        5）我们发现经典的数据结构比如 Queue 和 Stack 被归属到 LinearSeq(线性序列)
        6）大家注意 Scala 中的 Map 体系有一个 SortedMap，说明 Scala 的 Map 可以支持排序
        7）IndexedSeq 和 LinearSeq 的区别：
            （1）IndexedSeq 是通过索引来查找和定位，因此速度快，比如 String 就是一个索引集合，通过索引即可定位
            （2）LinearSeq 是线型的，即有头尾的概念，这种数据结构一般是通过遍历来查找

 */


object _28_Collection_集合_简介 {

}
