package org.example.kotlinspring.controller

import jakarta.validation.Valid
import org.example.kotlinspring.dto.InstructorDto
import org.example.kotlinspring.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/instructors")
class InstructorController(private val instructorService: InstructorService) {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createInstructor(@RequestBody @Valid instructorDto: InstructorDto): InstructorDto {
    return instructorService.createInstructor(instructorDto)
  }

}
