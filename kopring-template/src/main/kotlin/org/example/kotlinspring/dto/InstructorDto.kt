package org.example.kotlinspring.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDto(
  val id: Long?,

  @get:NotBlank(message = "Name is required")
  var name: String,
)
