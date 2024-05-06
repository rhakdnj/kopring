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
@Table(name = "Language_TEPS")
class LanguageTepsEntity(
  @Id
  @Column(name = "language_test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val languageTestId: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_test_id", nullable = false, updatable = false)
  val languageTest: LanguageTest,

  @Embedded
  val score: LanguageTepsScore,
) {
  fun getConvertedScore(): Int {
    val tepsScore = this.score.getTotalScore()
    return when {
      tepsScore == 0 -> 0
      tepsScore >= 570 -> 15
      tepsScore >= 545 -> 14
      tepsScore >= 520 -> 12
      tepsScore >= 495 -> 11
      tepsScore >= 470 -> 9
      tepsScore >= 445 -> 8
      else -> 6
    }
  }
}

@Embeddable
class LanguageTepsScore(
  @Column(name = "reading", nullable = false)
  var reading: Int,

  @Column(name = "listening", nullable = false)
  var listening: Int,

  @Column(name = "voca", nullable = false)
  var vocabulary: Int,

  @Column(name = "grammer", nullable = false)
  var grammar: Int,
) {
  init {
    for (score in intArrayOf(reading, listening, vocabulary, grammar)) {
      require(score in 0..60) { "Invalid TEPS score Please enter a score between 0 and 60" }
    }
  }

  fun getTotalScore(): Int {
    return this.reading + this.listening + this.vocabulary + this.grammar
  }
}
