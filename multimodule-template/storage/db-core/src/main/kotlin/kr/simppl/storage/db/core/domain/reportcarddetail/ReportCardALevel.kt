package kr.simppl.storage.db.core.domain.reportcarddetail

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.reportcard.ReportCard
import kr.simppl.storage.db.core.domain.reportcardsubject.ReportCardSubject

@Entity
@DiscriminatorValue(ReportCardDetail.A_LEVEL)
class ReportCardALevel(
  reportCard: ReportCard,
  subject: ReportCardSubject,

  @Column(name = "a_level_score", nullable = false)
  val aLevelScore: StandardizedALevelDegree,
) : ReportCardDetail(reportCard = reportCard, subject = subject) {
  enum class StandardizedALevelDegree(val value: String) {
    A_PLUS("A*"),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    U("U"),
  }
}
