package kr.simppl.storage.db.core.domain.reportcardsubject

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.Base

@Entity
@Table(name = "Report_Card_Subject")
class ReportCardSubject(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "name", nullable = false, length = 45)
  var name: String,

  // TODO: is_approve -> is_approved
  @Column(name = "is_approve", nullable = false, columnDefinition = "BIT")
  var isApproved: Boolean = false,
) : Base()
