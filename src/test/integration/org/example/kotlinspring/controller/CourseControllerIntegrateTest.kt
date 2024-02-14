package org.example.kotlinspring.controller

import org.assertj.core.api.Assertions
import org.example.kotlinspring.dto.CourseDto
import org.example.kotlinspring.entity.Course
import org.example.kotlinspring.repository.CourseRepository
import org.example.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrateTest {

  @Autowired
  lateinit var webTestClient: WebTestClient

  @Autowired
  lateinit var courseRepository: CourseRepository

  @BeforeEach
  fun setUp() {
    courseRepository.deleteAll()
    courseRepository.saveAll(courseEntityList)
  }

  @Test
  fun `강좌를 생성한다`() {
    val courseDto = CourseDto(null, "Kotlin", "BACKEND")

    val actual = webTestClient.post()
      .uri("/courses")
      .bodyValue(courseDto)
      .exchange()
      .expectStatus().isCreated
      .expectBody(CourseDto::class.java)
      .returnResult()
      .responseBody

    Assertions.assertThat(actual!!.id != null).isTrue
  }

  @Test
  fun `모든 강좌를 조회한다`() {
    val actual = webTestClient.get()
      .uri("/courses")
      .exchange()
      .expectStatus().isOk
      .expectBodyList(CourseDto::class.java)
      .returnResult()
      .responseBody

    Assertions.assertThat(actual).hasSize(3)
  }

  @Test
  fun `강좌를 업데이트한다`() {
    // existing course
    val course = Course(null, "Kotlin", "BACKEND")
    courseRepository.save(course)

    val updateDto = CourseDto(null, "React", "FRONTEND")

    val actual = webTestClient.put()
      .uri("/courses/{courseId}", course.id)
      .bodyValue(updateDto)
      .exchange()
      .expectStatus().isOk
      .expectBody(CourseDto::class.java)
      .returnResult()
      .responseBody

    val expectDto = CourseDto(course.id, "React", "FRONTEND")

    Assertions.assertThat(actual).isEqualTo(expectDto)
  }

  @Test
  fun `강좌를 삭제한다`() {
    // existing course
    val course = Course(null, "Kotlin", "BACKEND")
    courseRepository.save(course)

    val actual = webTestClient.delete()
      .uri("/courses/{courseId}", course.id)
      .exchange()
      .expectStatus().isNoContent
  }
}
