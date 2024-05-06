package kr.simppl.storage.db.core.domain.reportcarddetail.convert

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.reportcarddetail.ReportCardALevel

@Converter(autoApply = true)
class StandardizedALevelDegreeConverter : AttributeConverter<ReportCardALevel.StandardizedALevelDegree, String> {
  override fun convertToDatabaseColumn(attribute: ReportCardALevel.StandardizedALevelDegree): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): ReportCardALevel.StandardizedALevelDegree {
    return ReportCardALevel.StandardizedALevelDegree.entries.find { it.value == dbData }!!
  }
}
