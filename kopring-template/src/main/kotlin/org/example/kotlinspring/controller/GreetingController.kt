package org.example.kotlinspring.controller

import mu.KLogging
import org.example.kotlinspring.config.CurrencyConfig
import org.example.kotlinspring.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greetings")
class GreetingController(val greetingService: GreetingService, val currencyConfig: CurrencyConfig) {

  companion object : KLogging()

  @GetMapping("/{name}")
  fun hello(@PathVariable("name") name: String): String {
    logger.info { "Name is $name" }
    return greetingService.retrieveGreeting(name)
  }

  @GetMapping("/currency")
  fun currency(): String {
    return "Currency URL: ${currencyConfig.url}, Username: ${currencyConfig.username}, Key: ${currencyConfig.key}"
  }

}
