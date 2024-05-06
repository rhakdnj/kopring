package kr.simppl.storage.db.core.domain.admission.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.admission.Admission

@Converter(autoApply = true)
class AdmissionTypeConverter : AttributeConverter<Admission.Type, String> {
  override fun convertToDatabaseColumn(attribute: Admission.Type): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): Admission.Type {
    return Admission.Type.entries.find { it.value == dbData }!!
  }
}
