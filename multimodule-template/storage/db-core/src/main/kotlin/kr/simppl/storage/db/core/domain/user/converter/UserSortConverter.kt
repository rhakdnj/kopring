package kr.simppl.storage.db.core.domain.user.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.shared.common.enums.Role

@Converter(autoApply = true)
class UserSortConverter : AttributeConverter<Role, String> {
  override fun convertToDatabaseColumn(attribute: Role): String {
    return attribute.code
  }

  override fun convertToEntityAttribute(dbData: String): Role {
    return Role.entries.find { it.code == dbData }!!
  }
}
