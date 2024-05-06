package kr.simppl.storage.db.core.domain.reportcarddetail

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.reportcard.ReportCard
import kr.simppl.storage.db.core.domain.reportcardsubject.ReportCardSubject

@Entity
@Table(name = "Report_Card_Detail")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class ReportCardDetail(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_card_id", nullable = false, updatable = false)
  val reportCard: ReportCard,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subject_id", nullable = false, updatable = false)
  val subject: ReportCardSubject,
) {
  companion object {
    const val IB = "IB"
    const val AP = "AP"
    const val A_LEVEL = "A-LEVEL"
    const val LOCAL = "LOCAL"
    const val ETC = "ETC"
  }
}
