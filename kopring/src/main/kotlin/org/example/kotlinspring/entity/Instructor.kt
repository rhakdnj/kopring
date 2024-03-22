package org.example.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name = "instructor")
data class Instructor(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long?,
  var name: String,

  @OneToMany(
    mappedBy = "instructor",
    cascade = [CascadeType.ALL],
    orphanRemoval = true
  )
  val courses: List<Course> = mutableListOf()
) {
  override fun toString(): String {
    return "Instructor(id=$id, name=$name)"
  }
}
