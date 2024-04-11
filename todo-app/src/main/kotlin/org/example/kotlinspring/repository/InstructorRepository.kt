package org.example.kotlinspring.repository

import org.example.kotlinspring.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Long> {

}
