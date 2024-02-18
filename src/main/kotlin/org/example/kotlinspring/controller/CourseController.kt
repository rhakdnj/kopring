package org.example.kotlinspring.controller

import jakarta.validation.Valid
import org.example.kotlinspring.dto.CourseDto
import org.example.kotlinspring.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/courses")
class CourseController(private val courseService: CourseService) {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createCourse(@RequestBody @Valid courseDto: CourseDto): CourseDto {
    return courseService.createCourse(courseDto)
  }

  @GetMapping
  fun retrieveAll(): List<CourseDto> = courseService.retrieveAll()

  @PutMapping("/{courseId}")
  fun updateCourse(@PathVariable courseId: Long, @RequestBody courseDto: CourseDto): CourseDto =
    courseService.updateCourse(courseId, courseDto)

  @DeleteMapping("/{courseId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteCourse(@PathVariable courseId: Long) = courseService.deleteCourse(courseId)

}
