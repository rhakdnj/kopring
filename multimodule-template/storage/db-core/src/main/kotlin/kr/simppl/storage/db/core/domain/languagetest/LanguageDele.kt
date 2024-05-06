package kr.simppl.storage.db.core.domain.languagetest

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.useradmission.EuropeanDegree

@Entity
@Table(name = "Language_DELE")
class LanguageDeleEntity(
  @Id
  @Column(name = "language_test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val languageTestId: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_test_id", nullable = false, updatable = false)
  val languageTest: LanguageTest,

  @Enumerated(EnumType.STRING)
  @Column(name = "grade", nullable = false)
  var grade: EuropeanDegree,

  @Column(name = "total_score", nullable = false)
  var totalScore: Int,

  @Embedded
  val score: LanguageDeleScore,
)

@Embeddable
class LanguageDeleScore(
  @Column(name = "reading", nullable = false, columnDefinition = "DOUBLE")
  var reading: Int,

  @Column(name = "listening", nullable = false, columnDefinition = "DOUBLE")
  var listening: Int,

  @Column(name = "speaking", nullable = false, columnDefinition = "DOUBLE")
  var speaking: Int,

  @Column(name = "writing", nullable = false, columnDefinition = "DOUBLE")
  var writing: Int,
) {
  init {
    for (score in intArrayOf(reading, listening, speaking, writing)) {
      require(score in 5..25) { "Invalid DELE score Please enter a score between 5 and 25" }
    }
  }
}
