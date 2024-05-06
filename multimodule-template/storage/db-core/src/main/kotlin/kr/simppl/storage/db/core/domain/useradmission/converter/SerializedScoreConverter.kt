package kr.simppl.storage.db.core.domain.useradmission.converter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.useradmission.SerializedScore

@Converter(autoApply = true)
class SerializedScoreConverter : AttributeConverter<SerializedScore, String> {
  override fun convertToDatabaseColumn(attribute: SerializedScore?): String {
    return objectMapper.writeValueAsString(attribute)
  }

  override fun convertToEntityAttribute(dbData: String?): SerializedScore {
    return objectMapper.readValue(dbData, SerializedScore::class.java)
  }

  companion object {
    internal val objectMapper = ObjectMapper()
  }
}
