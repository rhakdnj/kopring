package kr.simppl.storage.db.core.domain.item

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.paymenthistory.PaymentHistory

@Entity
@Table(name = "Item")
class Item(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "name", nullable = false, length = 45)
  var name: String = "",
  @Column(name = "description", nullable = false, length = 150)
  var description: String = "",

  @Column(name = "period_description", nullable = true, length = 200)
  val periodDescription: String? = null,

  @Column(name = "price", nullable = false)
  var price: Int = 0,

  @Column(name = "original_price", nullable = false)
  var originalPrice: Int = 0,

  @Column(name = "is_available", nullable = false, columnDefinition = "BIT")
  var isAvailable: Boolean = true,

  @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
  var paymentHistories: MutableList<PaymentHistory> = mutableListOf(),
)
