package kr.simppl.storage.db.core.domain.reportcarddetail.convert

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.reportcarddetail.ReportCardIb

@Converter(autoApply = true)
class IBDifficultyLevelConverter : AttributeConverter<ReportCardIb.DifficultyLevel, String> {
  override fun convertToDatabaseColumn(attribute: ReportCardIb.DifficultyLevel): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): ReportCardIb.DifficultyLevel {
    return ReportCardIb.DifficultyLevel.entries.find { it.value == dbData }!!
  }
}
