package kr.simppl.storage.db.core.domain.award

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
@Table(name = "Award")
class Award(
  @Id
  @Column(name = "activity_award_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_award_id", nullable = false, updatable = false)
  val activityAward: ActivityAward,

  @Column(name = "name", nullable = false)
  var type: Type,

  @Column(name = "award_date", nullable = false)
  var awardDate: LocalDateTime,
) {
  /**
   * 활동 유형
   * 교과, 비교과
   */
  enum class Type(val value: String) {
    CA("curriculum"),
    EA("extra-curricular"),
  }
}
