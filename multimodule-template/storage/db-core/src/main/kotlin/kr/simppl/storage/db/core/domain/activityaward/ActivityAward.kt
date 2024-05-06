package kr.simppl.storage.db.core.domain.activityaward

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.Base
import kr.simppl.storage.db.core.domain.activity.Activity
import kr.simppl.storage.db.core.domain.award.Award
import kr.simppl.storage.db.core.domain.user.User

@Entity
@Table(name = "Activity_Award")
class ActivityAward(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "division", nullable = false)
  var division: Division,

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  var content: String,

  @Column(name = "transcript", nullable = false, columnDefinition = "TEXT")
  var transcript: String,

  @Column(name = "publishing_organization", nullable = false)
  val publisher: String,

  @OneToOne(mappedBy = "activityAward", fetch = FetchType.LAZY)
  val activity: Activity,

  @OneToOne(mappedBy = "activityAward", fetch = FetchType.LAZY)
  val award: Award,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,
) : Base() {
  /**
   * 수상, 활동 구분
   * 교내, 교외
   */
  enum class Division(val value: String) {
    IN("in"),
    OUT("out"),
  }
}
