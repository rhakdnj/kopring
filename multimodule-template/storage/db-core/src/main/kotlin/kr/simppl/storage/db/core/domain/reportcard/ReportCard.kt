package kr.simppl.storage.db.core.domain.reportcard

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
import kr.simppl.storage.db.core.domain.highschool.HighSchool
import kr.simppl.storage.db.core.domain.reportcarddetail.ReportCardDetail
import kr.simppl.storage.db.core.domain.user.User

@Entity
@Table(name = "Report_Card")
class ReportCard(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "grade", nullable = false)
  val grade: Int,

  @Column(name = "semester", nullable = false)
  val semester: Int,

  @Column(name = "gpa", nullable = false, columnDefinition = "FLOAT")
  var gpa: Double,

  @Column(name = "perfect_score", nullable = false, columnDefinition = "FLOAT")
  var perfectScore: Double,

  @Column(name = "curriculum", nullable = false)
  var highSchoolCurriculum: HighSchoolCurriculum,

  @OneToMany(mappedBy = "reportCard", cascade = [CascadeType.ALL], orphanRemoval = true)
  var reportCardDetailList: MutableList<ReportCardDetail> = mutableListOf(),

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "highschool_id", nullable = false, updatable = false)
  var highSchool: HighSchool,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  var user: User,
) : Base() {
  init {
    require(gpa in 0.00..100.00) { "Invalid gpa Please enter a gpa between 0 and 100" }
    require(perfectScore in 0.00..100.00) { "Invalid perfect score Please enter a score between 0 and 100" }
  }

  enum class HighSchoolCurriculum(val value: String) {
    IB("IB"),
    AP("AP"),
    A_LEVEL("A-LEVEL"),
    LOCAL("LOCAL"),
    ETC("ETC"),
  }
}
