package kr.simppl.storage.db.core.domain.university.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.university.University

@Converter(autoApply = true)
class UniversityCategoryConverter : AttributeConverter<University.Category, String> {
  override fun convertToDatabaseColumn(attribute: University.Category): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): University.Category {
    return University.Category.entries.find { it.value == dbData }!!
  }
}
