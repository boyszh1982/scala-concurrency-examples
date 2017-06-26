package org.learningconcurrency

/**
  * Created by Administrator on 2017-06-25.
  * 第一章，基础
  */

// step.1
class Printer(val greeting: String) {

  def printMessage():Unit = println(greeting + "!")

  def printNumber(x:Int):Unit = {
    println("Number:"+x)
  }

}

object Printer {
  def main(args: Array[String]): Unit = {
    val printy = new Printer("Hi")
    printy.printMessage()
    printy.printNumber(5)
  }
}

//step.2
object Test {
  val Pi = 3.14
}

//step.3
//特征,相当于接口
trait Logging {
  def log(s:String):Unit
  def warn(s:String):Unit = log("WARN:"+s)
  def error(s:String):Unit = log("ERROR:"+s)
}

class PrintLogging extends Logging {
  override def log(s: String): Unit = println(s)
}

object PrintLogging {
  def main(args: Array[String]): Unit = {
    val pl = new PrintLogging
    pl.warn("What ? ")
  }
}


//step.4 泛型
class Pair[P,Q](val first:P , val second:Q) {

}

//step.5 函数对象
object FuncObj {

  val twice1:Int => Int = (x:Int) => x * 2
  val twice2 = (x:Int) => x * 2
  val twice3:Int => Int = x => x * 2
  val twice4:Int => Int = _ * 2

  val twice2params1:(Int,String) => String = (x:Int,s:String) => { s + x }
  val twice2params2 = (x:Int,s:String) => { s + x}
  val twice2params3:(Int,String) => String = (x,s) => { s + x }
  //It's wrong !
  //val twice2params4:(Int,String) => String = _ * 2

  def main(args: Array[String]): Unit = {
    println(twice1(2))
    println(twice2(2))
    println(twice3(2))
    println(twice4(2))

    println(twice2params1(2,"1"))
    println(twice2params2(2,"1"))
    println(twice2params3(2,"1"))

  }
}

/**
   叫名参数
   body:空格= Unit
 */
object Test2 {


  def runTwice(body: => Unit ) = {
    body
    body
  }

  def main(args: Array[String]): Unit = {
    runTwice{
      println("Hello 叫名参数")
    }

  }
}

/**
for,
Scala 中所有循环最后都会调用 foreach方法
 */

object Test3 {

  def main(args: Array[String]): Unit = {

    for (i <- (0 until 10)   ) println("A."+i)

    for (i <- 0 until 10 ) println("B."+i)

    for (i <- 0.until(10)) println("C."+i)

    // final method
    (0 until 10 ).foreach(i => { println("D."+i) } )

    // for 推导语句 yield 改变了0到10的数据
    var negatives = for ( i <- 0.until(10) ) yield { 100 -i}
    println(negatives)

    // 使用 map 实现上面功能
    negatives = (0 until 10).map(f => { f * 100 })
    println(negatives)

    // for 使用更多的参数
    var pairs = for ( x <- 0 until 4 ; y <- 0 until 4 ) yield { (x,y) }
    println(pairs)

    // 使用 flatmap 实现上面功能 , what's the difference of flatMap and map
    /**
      * 我们可以在for推导语句中，嵌套任意数量的生成器表达式。Scala编译器会将这些表达式转换为一些列
      * 嵌套的flatMap方法调用语句，这些调用语句嵌套的最内层会有一条调用map方法的语句
      */
    pairs = (0 until 4).flatMap(x => { (0 until 4).map(y => (x,y)) } )
    println(pairs)

  }

}


/**
  * 常用集合
  */
object Test4 {

  def main(args: Array[String]): Unit = {
    /**
      * 序列 Seq[T] ; 映射 Map[T] ; 集合 Set[T]
      */
    var messages: Seq[String] = Seq("Hello","World","!")
    println(messages)

    /**
      * 插值字符串,以小写s开头，在字符串中包含$
      */
    val magic = 7
    val myMagicNumber = s"My magic number is $magic"
    println(myMagicNumber)
  }
}

/**
  * 模式匹配(Pattern matching) 相当于 在映射中找东西，类是加强的switch.
  */
object Test5 {
  def main(args: Array[String]): Unit = {
    // 定义并初始化一个映射
    val successors = Map(1->2,2->3,3->4)
    successors.get(1) match {
      case Some(n) => { println(s"Successor is : $n") }
      case None => println("Could not find successor.")
    }


    val queue:Option[String] = Option("Hello1")
    queue match {
      case Some(s) => { println(s"Hello Message : $s") }
      case None => { println( "Nothing .")}
    }
  }
}


/**
  * 操作符重载
  */
class Position(val x:Int ,val y:Int){
  //输入另外一个Position对象
  def +(that:Position) = { new Position( x + that.x , y + that.y) }
}
object Test6 {
  def main(args: Array[String]): Unit = {
    val p1 = new Position(1,1)
    val p2 = new Position(2,2)
    val p3 = p1 + p2
    val x3 = p3.x
    val y3 = p3.y
    println(s"p3.x=$x3,p3.y=$y3")
    println(s"p3.x=${p3.x},p3.y=${p3.y}")
  }
}


/**
  * 第一章练习题
  */

/**
  * 第一题，知识点，函数参数，call-by-name , 叫名函数
  * http://blog.csdn.net/asongoficeandfire/article/details/21889375
  */
object CallByName {
  var global_cnt = 100;
  /**
    * 使用传名调用时，在参数名称和参数类型中间有一个=》符号。
    * @param a
    * @param b
    * @return
    */
  def addByName(a:Int,b: =>Int) = { a + global_cnt }
  def addByName2(a:Int,b: =>Int) = {
    b
    a + global_cnt
  }
  def addByValue(a:Int,b:Int) = { a + global_cnt }
  def func(a:Int,b:Int):Int = {
    global_cnt = global_cnt - 10
    return global_cnt
  }
  def main(args: Array[String]): Unit = {
    //输入的func(1,2)并没有被执行过，因为在addByName中也没有被调用过
    println( addByName(1,func(1,2) ))  //返回101
    //输入的func(1,2)在传入addByValue前已经被调用过
    println( addByValue(1,func(1,2) ))  //返回91
    //在addByName2中有调用过b,即把func代码传入到addByName2后在其中被调用
    println( addByName2(1,func(1,2) ))  //返回81
  }

}

object Exercise_001 {
  /**
    * 题目一 def compose[A,B,C] (g:B=> C, f:A=>B):A=>C = a => ???
    * 返回一个h函数
    */


  def main(args: Array[String]): Unit = {

  }
}
