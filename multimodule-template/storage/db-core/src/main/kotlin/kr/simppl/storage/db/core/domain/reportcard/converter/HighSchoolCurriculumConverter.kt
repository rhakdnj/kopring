package kr.simppl.storage.db.core.domain.reportcard.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.reportcard.ReportCard

@Converter(autoApply = true)
class HighSchoolCurriculumConverter : AttributeConverter<ReportCard.HighSchoolCurriculum, String> {
  override fun convertToDatabaseColumn(attribute: ReportCard.HighSchoolCurriculum): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): ReportCard.HighSchoolCurriculum {
    return ReportCard.HighSchoolCurriculum.entries.find { it.value == dbData }!!
  }
}
