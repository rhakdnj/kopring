package org.example.kotlinspring.study

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
class PostConstructBean {
  @PostConstruct
  fun init() {
    println("Called PostConstruct")
  }
}

@Configuration
@ComponentScan
class PostConstructSample

fun main() {
  val context = AnnotationConfigApplicationContext(PostConstructSample::class.java)

  // No call @PostConstruct !
  val bean = PostConstructBean()
}
