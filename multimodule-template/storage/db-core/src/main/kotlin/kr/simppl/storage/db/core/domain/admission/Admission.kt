package kr.simppl.storage.db.core.domain.admission

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.Base
import kr.simppl.storage.db.core.domain.recruit.Recruit
import kr.simppl.storage.db.core.domain.useradmission.UserAdmission
import java.time.LocalDateTime

@Entity
@Table(name = "Admission")
class Admission(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "is_available", nullable = false, columnDefinition = "BIT")
  var isAvailable: Boolean = true,

  @Column(name = "start_date", nullable = false)
  val startDate: LocalDateTime,

  @Column(name = "end_date", nullable = false)
  val endDate: LocalDateTime,

  @Column(name = "sort", nullable = false)
  var sort: Sort,

  @Column(name = "type", nullable = false)
  val type: Type,

  @Column(name = "name", nullable = false, length = 45)
  val name: String,

  @Column(name = "high", nullable = false, columnDefinition = "FLOAT")
  var high: Double,

  @Column(name = "middle", nullable = false, columnDefinition = "FLOAT")
  var middle: Double,

  @Column(name = "low", nullable = false, columnDefinition = "FLOAT")
  var low: Double,

  @OneToMany(mappedBy = "admission", cascade = [CascadeType.ALL], orphanRemoval = true)
  var recruitList: MutableList<Recruit> = mutableListOf(),

  @OneToMany(mappedBy = "admission", cascade = [CascadeType.ALL], orphanRemoval = true)
  var userAdmissionList: MutableList<UserAdmission> = mutableListOf(),
) : Base() {
  /**
   * 입시 종류
   */
  enum class Sort(val value: String) {
    THREE_YEAR("3Y"),
    TWELVE_YEAR("12Y"),
  }

  /**
   * 입시 분류
   */
  enum class Type(val value: String) {
    MEDICAL("medical"),
    ECONOMIC("economic"),
    SOCIAL("social"),
    ENGINEERING("engineering"),
    HUMANITY("humanity"),
    SCIENCE("science"),
  }
}
