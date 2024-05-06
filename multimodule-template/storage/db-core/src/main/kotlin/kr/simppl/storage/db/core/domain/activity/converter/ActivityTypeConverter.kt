package kr.simppl.storage.db.core.domain.activity.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.activity.Activity

@Converter(autoApply = true)
class ActivityTypeConverter : AttributeConverter<Activity.Type, String> {
  override fun convertToDatabaseColumn(attribute: Activity.Type): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): Activity.Type {
    return Activity.Type.entries.find { it.value == dbData }!!
  }
}
