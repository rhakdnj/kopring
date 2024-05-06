package kr.simppl.storage.db.core.domain.activity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.activityaward.ActivityAward
import java.time.LocalDateTime

@Entity
@Table(name = "Activity")
class Activity(
  @Id
  @Column(name = "activity_award_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_award_id", nullable = false, updatable = false)
  val activityAward: ActivityAward,

  @Column(name = "name", nullable = false)
  var type: Type,

  @Column(name = "start_date", nullable = false)
  var startDate: LocalDateTime,

  @Column(name = "end_date", nullable = false)
  var endDate: LocalDateTime,
) {
  /**
   * 활동 유형
   * 자율, 탐구, 진로, 기타
   */
  enum class Type(val value: String) {
    AUTONOMY("autonomy"),
    INQUIRY("inquiry"),
    CAREER("career"),
    OTHER("other"),
  }
}
