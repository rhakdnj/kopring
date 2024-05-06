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
@Table(name = "Language_TOEIC")
class LanguageToeicEntity(
  @Id
  @Column(name = "language_test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val languageTestId: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_test_id", nullable = false, updatable = false)
  val languageTest: LanguageTest,
  @Embedded
  val score: LanguageToeicScore,
) {
  fun getConvertedScore(): Int {
    val toeicScore = this.score.getTotalScore()
    return when {
      toeicScore == 0 -> 0
      toeicScore >= 980 -> 12
      toeicScore >= 950 -> 10
      toeicScore >= 900 -> 8
      toeicScore >= 850 -> 6
      else -> 0
    }
  }
}

@Embeddable
class LanguageToeicScore(
  @Column(name = "reading", nullable = false)
  var reading: Int,

  @Column(name = "listening", nullable = false)
  var listening: Int,
) {
  init {
    for (score in intArrayOf(reading, listening)) {
      require(score in 0..495) { "Invalid TOEIC score Please enter a score between 0 and 495" }
    }
  }

  fun getTotalScore(): Int {
    return this.reading + this.listening
  }
}
