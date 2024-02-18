package org.example.kotlinspring.repository

import org.assertj.core.api.Assertions
import org.example.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntegrateTest {

  @Autowired
  lateinit var courseRepository: CourseRepository

  @BeforeEach
  fun setUp() {
    courseRepository.deleteAll()
    courseRepository.saveAll(courseEntityList)
  }

  @ParameterizedTest
  @MethodSource("courseAndSize")
  fun findByNameContaining(name: String, expectedSize: Int) {
    val actual = courseRepository.findByNameContaining(name)

    Assertions.assertThat(actual).hasSize(expectedSize)
  }

  companion object {
    @JvmStatic
    fun courseAndSize() = listOf(
      arrayOf("Kotlin", 1),
      arrayOf("React", 1),
      arrayOf("Docker", 1)
    )
  }

}
