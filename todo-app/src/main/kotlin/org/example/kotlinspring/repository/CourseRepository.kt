package org.example.kotlinspring.repository

import org.example.kotlinspring.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Long> {

  fun findByNameContaining(courseName: String): List<Course>

}
