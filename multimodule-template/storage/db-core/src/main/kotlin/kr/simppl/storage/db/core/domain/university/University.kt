package kr.simppl.storage.db.core.domain.university

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.recruit.Recruit
import kr.simppl.storage.db.core.domain.useradmission.UserAdmission

@Entity
@Table(name = "University")
class University(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "applied_count", nullable = false)
  var appliedCount: Int = 0,

  @Column(name = "is_main", nullable = false)
  var isMain: Boolean = false,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_university_id", nullable = true, updatable = false)
  var parentUniversity: University? = null,

  @Column(name = "name", nullable = false, length = 45)
  val name: String,

  @Column(name = "image", nullable = true, columnDefinition = "TEXT")
  var image: String? = null,

  @Column(name = "category", nullable = false)
  var category: Category,

  @OneToMany(mappedBy = "parentUniversity", cascade = [CascadeType.ALL], orphanRemoval = true)
  var childUniversityList: MutableList<University> = mutableListOf(),

  @OneToMany(mappedBy = "university", cascade = [CascadeType.ALL], orphanRemoval = true)
  var recruitList: MutableList<Recruit> = mutableListOf(),

  @OneToMany(mappedBy = "university", cascade = [CascadeType.ALL], orphanRemoval = true)
  var userAdmissionList: MutableList<UserAdmission> = mutableListOf(),
) {
  enum class Category(val value: String) {
    UNIVERSITY("university"),
    FACULTY("faculty"),
    DEPARTMENT("department"),
  }
}
