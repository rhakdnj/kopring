package kr.simppl.storage.db.core.domain.reportcarddetail

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.reportcard.ReportCard
import kr.simppl.storage.db.core.domain.reportcardsubject.ReportCardSubject

@Entity
@DiscriminatorValue(ReportCardDetail.IB)
class ReportCardIb(
  reportCard: ReportCard,
  subject: ReportCardSubject,

  @Column(name = "ib_score", nullable = false)
  val score: String,

  @Column(name = "ib_difficulty", nullable = false)
  val difficulty: DifficultyLevel,
) : ReportCardDetail(reportCard = reportCard, subject = subject) {
  enum class DifficultyLevel(val value: String) {
    HIGH_LEVEL("HL"),
    LOW_LEVEL("SL"), // TODO: Standard Level 변경
  }
}
