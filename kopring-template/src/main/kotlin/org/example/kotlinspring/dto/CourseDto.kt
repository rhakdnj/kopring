package org.example.kotlinspring.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDto(
  val id: Long?,

  @get:NotBlank(message = "Name is required")
  val name: String,

  @get:NotBlank(message = "Category is required")
  val category: String,

  @get:NotNull(message = "Instructor must not be null")
  val instructorId: Long? = null,
)
