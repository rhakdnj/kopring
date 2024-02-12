package org.example.kotlinspring.controller

import org.example.kotlinspring.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greetings")
class GreetingController(val greetingService: GreetingService) {

  @GetMapping("/{name}")
  fun hello(@PathVariable("name") name: String): String {
    return greetingService.retrieveGreeting(name)
  }

}
