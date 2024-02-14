package org.example.kotlinspring.service

import mu.KLogging
import org.example.kotlinspring.dto.CourseDto
import org.example.kotlinspring.entity.Course
import org.example.kotlinspring.exception.CourseNotFoundException
import org.example.kotlinspring.repository.CourseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(private val courseRepository: CourseRepository) {

  companion object : KLogging()

  @Transactional
  fun createCourse(courseDto: CourseDto): CourseDto {
    val courseEntity = courseDto.let {
      Course(null, it.name, it.category)
    }

    courseRepository.save(courseEntity)

    logger.info { "Saved course is: $courseEntity" }

    return courseEntity.let {
      CourseDto(it.id, it.name, it.category)
    }
  }

  fun retrieveAll(): List<CourseDto> {
    return courseRepository.findAll()
      .map {
        CourseDto(it.id, it.name, it.category)
      }
  }

  fun updateCourse(courseId: Long, courseDto: CourseDto): CourseDto {
    val existingCourse = courseRepository.findById(courseId)
      .orElseThrow { CourseNotFoundException("No Course found for the passed in id $courseId") }

    return existingCourse.let {
      it.changeName(courseDto.name)
      it.changeCategory(courseDto.category)
      courseRepository.save(it)
      CourseDto(it.id, it.name, it.category)
    }
  }

  fun deleteCourse(courseId: Long) {
    val existingCourse = courseRepository.findById(courseId)
      .orElseThrow { CourseNotFoundException("No Course found for the passed in id $courseId") }

    courseRepository.delete(existingCourse)
  }

}