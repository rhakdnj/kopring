package kr.simppl.storage.db.core.domain.useradmission

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.Base
import kr.simppl.storage.db.core.domain.admission.Admission
import kr.simppl.storage.db.core.domain.university.University
import kr.simppl.storage.db.core.domain.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "User_Admission")
class UserAdmission(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "admission_id", nullable = false, updatable = false)
  val admission: Admission,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "university_id", nullable = false, updatable = false)
  val university: University,

  @Column(name = "applied_at", nullable = true)
  var appliedAt: LocalDateTime? = null,

  @Column(name = "exposed_at", nullable = true)
  var exposedAt: LocalDateTime? = null,

  @Column(name = "is_approved", nullable = false, columnDefinition = "BIT")
  var isApproved: Boolean = false,

  @Column(name = "score", nullable = true, columnDefinition = "FLOAT")
  var score: Double? = 0.0,

  @Embedded
  @Column(name = "serialized_score", nullable = true, columnDefinition = "TEXT")
  var serializedScore: SerializedScore? = null,

  @Column(name = "reject_reason", nullable = false, length = 300, columnDefinition = "VARCHAR")
  var rejectReason: String = "",
) : Base()
