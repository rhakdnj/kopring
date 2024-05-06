package kr.simppl.storage.db.core.domain.paymenthistory

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.item.Item
import kr.simppl.storage.db.core.domain.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "Payment_Histories")
class PaymentHistory(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "paid_at", nullable = true)
  var paidAt: LocalDateTime? = LocalDateTime.now(),

  @Column(name = "status", nullable = false)
  var status: Status = Status.PAID,

  @Column(name = "count", nullable = false)
  var count: Int = 0,

  @Column(name = "tid", nullable = true, length = 45)
  var tid: String,

  @Column(name = "merchantUid", nullable = true, length = 45)
  var merchantUid: String,

  @Column(name = "vbank_holder", nullable = true, length = 45)
  var virtualBankHolder: String?,

  @Column(name = "vbank_name", nullable = true, length = 45)
  var virtualBankName: String?,

  @Column(name = "vbank_num", nullable = true, length = 45)
  var virtualBankNum: String?,

  @Column(name = "pay_method", nullable = false)
  val payMethod: PayMethod,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false, updatable = false)
  var item: Item,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,
) {
  enum class PayMethod(val value: String) {
    CARD("card"),
    TRANS("trans"),
  }

  enum class Status(val value: String) {
    PAID("paid"),
    CANCELLED("cancelled"),
    READY("ready"),
  }
}
