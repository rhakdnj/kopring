package org.example.kotlinspring.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions
import org.example.kotlinspring.service.GreetingService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

  @Autowired
  lateinit var webTestClient: WebTestClient

  @MockkBean
  lateinit var greetingServiceMock: GreetingService

  @Test
  fun retrieveGreeting() {
    val name = "Jaime"

    every { greetingServiceMock.retrieveGreeting(name) } returns "Hello $name, Hello from Test Profile!"

    val result = webTestClient.get()
      .uri("/greetings/{name}", name)
      .exchange()
      .expectStatus().is2xxSuccessful
      .expectBody(String::class.java)
      .returnResult()

    Assertions.assertThat("Hello $name, Hello from Test Profile!").isEqualTo(result.responseBody)
  }

}
