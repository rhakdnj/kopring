package org.example.kotlinspring.study

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
class Normal

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
class Prototype

@Configuration
@ComponentScan
class BeanScope

fun main() {

  val context = AnnotationConfigApplicationContext(BeanScope::class.java)

  context.getBean(Normal::class.java).let(::println)
  context.getBean(Normal::class.java).let(::println)

  context.getBean(Prototype::class.java).let(::println)
  context.getBean(Prototype::class.java).let(::println)
}
