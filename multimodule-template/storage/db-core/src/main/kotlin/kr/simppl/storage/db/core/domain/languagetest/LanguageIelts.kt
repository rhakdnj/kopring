package kr.simppl.storage.db.core.domain.languagetest

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "Language_IELTS")
class LanguageIeltsEntity(
  @Id
  @Column(name = "language_test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val languageTestId: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_test_id", nullable = false, updatable = false)
  val languageTest: LanguageTest,

  @Embedded
  val score: LanguageIeltsScore,

  @Column(name = "total_score", nullable = false)
  var totalScore: Double,
) {
  fun getConvertedScore(): Int {
    return when (this.totalScore) {
      9.0 -> 15
      8.5 -> 14
      8.0, 7.5 -> 10
      7.0, 6.5 -> 6
      else -> 0
    }
  }
}

@Embeddable
class LanguageIeltsScore(
  @Column(name = "reading", nullable = false, columnDefinition = "DOUBLE")
  var reading: Double,

  @Column(name = "listening", nullable = false, columnDefinition = "DOUBLE")
  var listening: Double,

  @Column(name = "speaking", nullable = false, columnDefinition = "DOUBLE")
  var speaking: Double,

  @Column(name = "writing", nullable = false, columnDefinition = "DOUBLE")
  var writing: Double,
) {
  init {
    for (score in doubleArrayOf(reading, listening, speaking, writing)) {
      require(score in 0.00..9.00) { "Invalid IELTS score Please enter a score between 0 and 9" }
      require(score % 0.5 == 0.0) { "Invalid IELTS score Please enter a score in 0.5 increments" }
    }
  }
}
