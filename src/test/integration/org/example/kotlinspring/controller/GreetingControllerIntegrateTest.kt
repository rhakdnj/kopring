package org.example.kotlinspring.controller

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class GreetingControllerIntegrateTest {

  @Autowired
  lateinit var webTestClient: WebTestClient

  @Test
  fun retrieveGreeting() {
    val name = "Jaime"

    val result = webTestClient.get()
      .uri("/greetings/{name}", name)
      .exchange()
      .expectStatus().is2xxSuccessful
      .expectBody(String::class.java)
      .returnResult()

    Assertions.assertThat("Hello $name, Hello from Test Profile!").isEqualTo(result.responseBody)
  }

}
