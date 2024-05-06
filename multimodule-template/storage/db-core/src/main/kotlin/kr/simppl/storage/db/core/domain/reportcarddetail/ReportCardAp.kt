package kr.simppl.storage.db.core.domain.reportcarddetail

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.reportcard.ReportCard
import kr.simppl.storage.db.core.domain.reportcardsubject.ReportCardSubject

@Entity
@DiscriminatorValue(ReportCardDetail.AP)
class ReportCardAp(
  reportCard: ReportCard,
  subject: ReportCardSubject,

  @Column(name = "ap_score", nullable = false)
  val score: String,

  @Column(name = "is_ap", nullable = false, columnDefinition = "BIT")
  val isAP: Boolean = false,
) : ReportCardDetail(reportCard = reportCard, subject = subject)
