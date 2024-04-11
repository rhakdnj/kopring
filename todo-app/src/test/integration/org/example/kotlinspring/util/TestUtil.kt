package org.example.kotlinspring.util

import org.example.kotlinspring.entity.Course
import org.example.kotlinspring.entity.Instructor

fun courseEntityList(instructor: Instructor? = null) : List<Course> = listOf(
  Course(null, "Kotlin", "BACKEND", instructor),
  Course(null, "React", "FRONTEND", instructor),
  Course(null, "Docker", "DEVOPS", instructor)
)

fun instructorEntitty(name: String = "Test Instructor") = Instructor(null, name)
