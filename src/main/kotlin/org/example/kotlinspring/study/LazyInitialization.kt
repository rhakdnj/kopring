package org.example.kotlinspring.study

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Component
class A {
}

@Component
class B(val a: A) {
  init {
    println("Eager Initialization")
  }
}


@Configuration
@ComponentScan
class LazyInitialization

fun main() {

  val context = AnnotationConfigApplicationContext(LazyInitialization::class.java)

  /**
   * class B Bean 을 사용하지 않더라도 Eager Initialization of B 가 출력된다.
   *
   * Bean을 load하지도 않고 메서드를 호출하지 않더라도 자동으로 Bean이 초기화된다.
   */
  println("Context Created")
}
