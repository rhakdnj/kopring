package kr.simppl.storage.db.core.domain.activityaward.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.activityaward.ActivityAward

@Converter(autoApply = true)
class ActivityAwardDivisionConverter : AttributeConverter<ActivityAward.Division, String> {
  override fun convertToDatabaseColumn(attribute: ActivityAward.Division): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): ActivityAward.Division {
    return ActivityAward.Division.entries.find { it.value == dbData }!!
  }
}
