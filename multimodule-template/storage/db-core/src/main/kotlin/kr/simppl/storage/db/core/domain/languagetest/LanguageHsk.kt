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
import kr.simppl.storage.db.core.domain.useradmission.HSKDegree

@Entity
@Table(name = "Language_HSK")
class LanguageHskEntity(
  @Id
  @Column(name = "language_test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val languageTestId: Long? = null,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_test_id", nullable = false, updatable = false)
  val languageTest: LanguageTest,

  @Embedded
  val score: LanguageHskScore,

  @Column(name = "grade", nullable = false, length = 45)
  var grade: HSKDegree,
) {
  fun getTotalScore(): Int {
    return this.score.reading + this.score.listening
  }

  fun getConvertedScore(): Double {
    val hskScore = this.getTotalScore()
    return when {
      this.grade == HSKDegree.GRADE6 && hskScore >= 250 -> 5.0
      this.grade == HSKDegree.GRADE6 -> 4.5
      this.grade == HSKDegree.GRADE5 -> 4.0
      this.grade == HSKDegree.GRADE4 -> 3.0
      else -> 0.0
    }
  }
}

@Embeddable
class LanguageHskScore(
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
      require(score in 0..100) { "Invalid DELF score Please enter a score between 0 and 100" }
    }
  }
}
