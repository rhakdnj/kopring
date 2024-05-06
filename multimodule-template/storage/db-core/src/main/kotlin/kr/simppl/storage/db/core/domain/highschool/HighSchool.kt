package kr.simppl.storage.db.core.domain.highschool

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
import kr.simppl.storage.db.core.domain.Base
import kr.simppl.storage.db.core.domain.country.Country
import kr.simppl.storage.db.core.domain.reportcard.ReportCard

@Entity
@Table(name = "Highschool")
class HighSchool(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "name", nullable = false, length = 45)
  val name: String,

  // TODO: is_approve -> is_approved
  @Column(name = "is_approve", nullable = false, columnDefinition = "BIT")
  var isApproved: Boolean = false,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = false, updatable = false)
  val country: Country,

  @OneToMany(mappedBy = "highSchool", cascade = [CascadeType.ALL], orphanRemoval = true)
  val reportCardList: MutableList<ReportCard> = mutableListOf(),
) : Base()
