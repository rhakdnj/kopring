package org.example.kotlinspring.controller

import org.assertj.core.api.Assertions
import org.example.kotlinspring.dto.CourseDto
import org.example.kotlinspring.entity.Course
import org.example.kotlinspring.repository.CourseRepository
import org.example.kotlinspring.repository.InstructorRepository
import org.example.kotlinspring.util.courseEntityList
import org.example.kotlinspring.util.instructorEntitty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrateTest {

  @Autowired
  lateinit var webTestClient: WebTestClient

  @Autowired
  lateinit var courseRepository: CourseRepository

  @Autowired
  lateinit var instructorRepository: InstructorRepository

  @BeforeEach
  fun setUp() {
    courseRepository.deleteAll()
    instructorRepository.deleteAll()

    val instructor = instructorRepository.save(instructorEntitty())

    courseRepository.saveAll(courseEntityList(instructor))
  }

  @Test
  fun `강좌를 생성한다`() {
    val instructor = instructorRepository.findAll().first()

    val courseDto = CourseDto(null, "Kotlin", "BACKEND", instructor.id)

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
  fun `강좌 이름을 통해 조회한다`() {
    val uri = UriComponentsBuilder.fromUriString("/courses")
      .queryParam("courseName", "Kotlin")
      .toUriString()

    val actual = webTestClient.get()
      .uri(uri)
      .exchange()
      .expectStatus().isOk
      .expectBodyList(CourseDto::class.java)
      .returnResult()
      .responseBody

    Assertions.assertThat(actual).hasSize(1)
  }


  @Test
  fun `강좌를 업데이트한다`() {
    // existing course
    val instructor = instructorRepository.findAll().first()

    val course = Course(null, "Kotlin", "BACKEND", instructor)
    courseRepository.save(course)

    val updateDto = CourseDto(null, "React", "FRONTEND", instructor.id)

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
    val instructor = instructorRepository.findAll().first()

    // existing course
    val course = Course(null, "Kotlin", "BACKEND", instructor)
    courseRepository.save(course)

    val actual = webTestClient.delete()
      .uri("/courses/{courseId}", course.id)
      .exchange()
      .expectStatus().isNoContent
  }
}
