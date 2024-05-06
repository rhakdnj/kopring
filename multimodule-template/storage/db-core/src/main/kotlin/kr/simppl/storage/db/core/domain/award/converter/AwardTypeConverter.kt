package kr.simppl.storage.db.core.domain.award.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.award.Award

@Converter(autoApply = true)
class AwardTypeConverter : AttributeConverter<Award.Type, String> {
  override fun convertToDatabaseColumn(attribute: Award.Type): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): Award.Type {
    return Award.Type.entries.find { it.value == dbData }!!
  }
}
