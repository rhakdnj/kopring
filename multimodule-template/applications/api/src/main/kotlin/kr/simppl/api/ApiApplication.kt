package kr.simppl.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = ["kr.simppl"])
@SpringBootApplication(scanBasePackages = ["kr.simppl"])
class ApiApplication

fun main(args: Array<String>) {
  runApplication<ApiApplication>(*args)
}
