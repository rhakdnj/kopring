package kr.simppl.storage.db.core.domain.paymenthistory.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.paymenthistory.PaymentHistory

@Converter(autoApply = true)
class PayStatusConverter : AttributeConverter<PaymentHistory.Status, String> {
  override fun convertToDatabaseColumn(attribute: PaymentHistory.Status): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): PaymentHistory.Status {
    return PaymentHistory.Status.entries.find { it.value == dbData }!!
  }
}
