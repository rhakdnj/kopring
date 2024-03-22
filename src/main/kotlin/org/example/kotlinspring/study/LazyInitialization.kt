package org.example.kotlinspring.study

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
class A {
}

@Component
@Lazy
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
   * 호출되는 시점의 @Bean 의 지연 초기화
   */
  context.getBean(B::class.java).let(::println)
}
