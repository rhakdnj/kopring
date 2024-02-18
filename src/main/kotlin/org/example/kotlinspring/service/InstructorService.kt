package org.example.kotlinspring.service

import org.example.kotlinspring.dto.InstructorDto
import org.example.kotlinspring.entity.Instructor
import org.example.kotlinspring.repository.InstructorRepository
import org.springframework.stereotype.Service

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

  fun createInstructor(instructorDto: InstructorDto): InstructorDto {
    return instructorRepository.save(
      instructorDto.let {
        Instructor(null, it.name)
      })
      .let { InstructorDto(it.id, it.name) }
  }

}
