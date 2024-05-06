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
@Table(name = "Language_TOEFL")
class LanguageToeflEntity(
  @Id
  @Column(name = "language_test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val languageTestId: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_test_id", nullable = false, updatable = false)
  val languageTest: LanguageTest,

  @Embedded
  val score: LanguageToeflScore,
) {
  fun getConvertedScore(): Int {
    val toeflScore = this.score.getTotalScore()
    return when {
      toeflScore == 0 -> 0
      toeflScore >= 115 -> 15
      toeflScore >= 110 -> 14
      toeflScore >= 105 -> 12
      toeflScore >= 100 -> 11
      toeflScore >= 95 -> 9
      toeflScore >= 90 -> 8
      else -> 6
    }
  }
}

@Embeddable
class LanguageToeflScore(
  @Column(name = "reading", nullable = false)
  var reading: Int,

  @Column(name = "listening", nullable = false)
  var listening: Int,

  @Column(name = "speaking", nullable = false)
  var speaking: Int,

  @Column(name = "writing", nullable = false)
  var writing: Int,
) {
  init {
    for (score in intArrayOf(reading, listening, speaking, writing)) {
      require(score in 0..30) { "Invalid TOEFL score Please enter a score between 0 and 30" }
    }
  }

  fun getTotalScore(): Int {
    return this.reading + this.listening + this.speaking + this.writing
  }
}
