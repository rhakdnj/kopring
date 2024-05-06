package kr.simppl.storage.db.core.domain.useradmission

import jakarta.persistence.Embeddable

@Embeddable
data class SerializedScore(
  var localScore: Int,
  var toeflScore: Int,
  var ieltsScore: Int,
  var tepsScore: Int,
  var toeicScore: Int,
  var hskRank: HSKDegree?,
  var hskScore: Int,
  var deleRank: EuropeanDegree?,
  var deleScore: Int,
  var delfRank: EuropeanDegree?,
  var delfScore: Int,
  var ibScore: Int,
  var apScore: Int,
  var satScore: Int,
  var aLevelScore: Int,
  var actScore: Int,
  var awardScore: Int,
  var activityScore: Int,
)

enum class HSKDegree(val value: Int) {
  GRADE1(1),
  GRADE2(2),
  GRADE3(3),
  GRADE4(4),
  GRADE5(5),
  GRADE6(6),
}

enum class EuropeanDegree(val score: Int = 0) {
  A1,
  A2,
  B1(3),
  B2(4),
  C1(5),
  C2(5),
}
