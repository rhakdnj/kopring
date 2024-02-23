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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "instructor_id", nullable = false)
  val instructor: Instructor? = null
) {
  fun changeName(newName: String) {
    name = newName
  }

  fun changeCategory(newCategory: String) {
    category = newCategory
  }

  override fun toString(): String {
    return "Course(id=$id, name='$name', category='$category', instructor=${instructor?.id})"
  }
}
