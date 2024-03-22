package org.example.kotlinspring.study

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
data class ConstructorInjection(
  val dependency1: Dependency1,
  val dependency2: Dependency2,
)

@Component
class FieldInjection {
  @Autowired
  lateinit var dependency1: Dependency1
  @Autowired
  lateinit var dependency2: Dependency2
}

@Component
class Dependency1

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
class Dependency2

@Configuration
@ComponentScan
class DependencyInjection

fun main() {

  val context = AnnotationConfigApplicationContext(DependencyInjection::class.java)

  val fieldInjection = context.getBean(FieldInjection::class.java)
  println(fieldInjection.dependency2)

  val fieldInjection2 = context.getBean(FieldInjection::class.java)
  println(fieldInjection2.dependency2)
}
