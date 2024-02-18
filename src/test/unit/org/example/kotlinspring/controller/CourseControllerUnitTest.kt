package org.example.kotlinspring.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions
import org.example.kotlinspring.dto.CourseDto
import org.example.kotlinspring.entity.Course
import org.example.kotlinspring.service.CourseService
import org.example.kotlinspring.service.GreetingService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController ::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

  @Autowired
  lateinit var webTestClient: WebTestClient

  @MockkBean
  lateinit var courseServiceMock: CourseService

  @Test
  fun `강좌를 생성한다`() {
    val courseDto = CourseDto(null, "Kotlin", "BACKEND")

    every { courseServiceMock.createCourse(courseDto) } answers {
      CourseDto(1, "Kotlin", "BACKEND")
    }

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
  fun `이름, 카테고리가 없으면 400 에러를 던지며, 해당하는 에러 메시지를 바디에 담은다`() {
    val courseDto = CourseDto(null, "", "")

    every { courseServiceMock.createCourse(courseDto) } answers {
      CourseDto(1, "Kotlin", "BACKEND")
    }

    val actual = webTestClient.post()
      .uri("/courses")
      .bodyValue(courseDto)
      .exchange()
      .expectStatus().isBadRequest
      .expectBody(String::class.java)
      .returnResult()
      .responseBody

    Assertions.assertThat(actual).isEqualTo("Category is required, Name is required")
  }

  @Test
  fun `runtimeException`() {
    val courseDto = CourseDto(null, "Kotlin", "BACKEND")

    val message = "Unexpected Error Occurred"
    every { courseServiceMock.createCourse(courseDto) } throws RuntimeException(message)

    val actual = webTestClient.post()
      .uri("/courses")
      .bodyValue(courseDto)
      .exchange()
      .expectStatus().is5xxServerError
      .expectBody(String::class.java)
      .returnResult()
      .responseBody

    Assertions.assertThat(actual).isEqualTo(message)
  }


  @Test
  fun `모든 강좌를 조회한다`() {
    every { courseServiceMock.retrieveAll() } answers {
      listOf(
        CourseDto(1, "Kotlin", "BACKEND"),
        CourseDto(2, "React", "FRONTEND"),
        CourseDto(3, "Spring", "BACKEND")
      )
    }

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
    every { courseServiceMock.updateCourse(any(), any()) } answers {
      CourseDto(1, "React", "FRONTEND")
    }

    val updateDto = CourseDto(null, "React", "FRONTEND")

    val actual = webTestClient.put()
      .uri("/courses/{courseId}", 1)
      .bodyValue(updateDto)
      .exchange()
      .expectStatus().isOk
      .expectBody(CourseDto::class.java)
      .returnResult()
      .responseBody

    val expectDto = CourseDto(1, "React", "FRONTEND")

    Assertions.assertThat(actual).isEqualTo(expectDto)
  }

  @Test
  fun `강좌를 삭제한다`() {
    every { courseServiceMock.deleteCourse(any()) } just runs

    val actual = webTestClient.delete()
      .uri("/courses/{courseId}", 1)
      .exchange()
      .expectStatus().isNoContent
  }

}
