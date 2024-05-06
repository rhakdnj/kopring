package kr.simppl.storage.db.core.domain.country

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.highschool.HighSchool

@Entity
@Table(name = "Country")
class Country(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "name", nullable = false, length = 45)
  val name: String,

  // TODO: is_approve -> is_approved
  @Column(name = "is_approve", nullable = false, columnDefinition = "BIT")
  var isApproved: Boolean = false,

  @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true)
  val highSchoolList: MutableList<HighSchool> = mutableListOf(),
)
