package org

/**
  * Created by Administrator on 2017-06-25.
  * 保对象，定义顶层方法log
  */
package object learningconcurrency {
  def log(msg:String):Unit = {
    println(s"${Thread.currentThread().getName}: $msg")
  }
}
