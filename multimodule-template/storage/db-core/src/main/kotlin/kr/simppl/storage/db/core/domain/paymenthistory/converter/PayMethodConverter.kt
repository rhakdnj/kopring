package kr.simppl.storage.db.core.domain.paymenthistory.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.simppl.storage.db.core.domain.paymenthistory.PaymentHistory

@Converter(autoApply = true)
class PayMethodConverter : AttributeConverter<PaymentHistory.PayMethod, String> {
  override fun convertToDatabaseColumn(attribute: PaymentHistory.PayMethod): String {
    return attribute.value
  }

  override fun convertToEntityAttribute(dbData: String): PaymentHistory.PayMethod {
    return PaymentHistory.PayMethod.entries.find { it.value == dbData }!!
  }
}
