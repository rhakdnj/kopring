package kr.simppl.storage.db.core.domain.reportcarddetail

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.reportcard.ReportCard
import kr.simppl.storage.db.core.domain.reportcardsubject.ReportCardSubject

@Entity
@DiscriminatorValue(ReportCardDetail.ETC)
class ReportCardEtc(
  reportCard: ReportCard,
  subject: ReportCardSubject,

  @Column(name = "name", nullable = true, length = 45)
  val name: String,

  @Column(name = "etc_score", nullable = true, length = 45)
  val score: String,

  @Column(name = "total_score", nullable = true, length = 45)
  val totalScore: String,
) : ReportCardDetail(reportCard = reportCard, subject = subject)
