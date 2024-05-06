package kr.simppl.storage.db.core.domain.reportcardfile

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.Base
import kr.simppl.storage.db.core.domain.user.User

@Entity
@Table(name = "Report_Card_File")
class ReportCardFile(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  @Column(name = "transcript", nullable = false, columnDefinition = "TEXT")
  var transcript: String,
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,
) : Base()
