package kr.simppl.storage.db.core.domain.admission.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.admission.Admission

@Converter(autoApply = true)
class AdmissionSortConverter : AttributeConverter<Admission.Sort, String> {
  override fun convertToDatabaseColumn(attribute: Admission.Sort): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): Admission.Sort {
    return Admission.Sort.entries.find { it.value == dbData }!!
  }
}
