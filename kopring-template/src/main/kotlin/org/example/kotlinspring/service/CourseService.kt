package org.example.kotlinspring.service

import mu.KLogging
import org.example.kotlinspring.dto.CourseDto
import org.example.kotlinspring.entity.Course
import org.example.kotlinspring.exception.CourseNotFoundException
import org.example.kotlinspring.repository.CourseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
  val courseRepository: CourseRepository,
  val instructorService: InstructorService,
  ) {

  companion object : KLogging()

  @Transactional
  fun createCourse(courseDto: CourseDto): CourseDto {
    val instructor = instructorService.findInstructor(courseDto.instructorId!!)

    val courseEntity = courseDto.let {
      Course(null, it.name, it.category, instructor)
    }

    courseRepository.save(courseEntity)

    logger.info { "Saved course is: $courseEntity" }

    return courseEntity.let {
      CourseDto(it.id, it.name, it.category, it.instructor?.id)
    }
  }

  fun retrieveAll(courseName: String?): List<CourseDto> {
    val courseList = courseName?.let {
      courseRepository.findByNameContaining(courseName)
    } ?: courseRepository.findAll()

    return courseList
      .map { CourseDto(it.id, it.name, it.category) }
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
