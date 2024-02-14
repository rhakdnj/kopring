package org.example.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name = "course")
data class Course(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long?,
  var name: String,
  var category: String,
) {
  fun changeName(newName: String) {
    name = newName
  }

  fun changeCategory(newCategory: String) {
    category = newCategory
  }
}
