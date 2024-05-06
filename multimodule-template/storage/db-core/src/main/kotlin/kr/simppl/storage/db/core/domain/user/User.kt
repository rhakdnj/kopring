package kr.simppl.storage.db.core.domain.user

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.simppl.shared.common.enums.Role
import kr.simppl.storage.db.core.domain.BaseTime
import kr.simppl.storage.db.core.domain.paymenthistory.PaymentHistory

@Entity
@Table(name = "Users")
class User(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "sort", nullable = false)
  var role: Role = Role.ROLE_USER,

  @Column(name = "email", nullable = false, length = 45)
  val email: String,

  @Column(name = "password", nullable = false, columnDefinition = "TEXT")
  var password: String = "NO_PASSWORD",

  @Column(name = "name", nullable = false, length = 10)
  val name: String,

  @Column(name = "nickname", nullable = false, length = 30, unique = true)
  var nickname: String,

  @Column(name = "birth", nullable = false, length = 8)
  var birth: String,

  @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
  var paymentHistories: MutableList<PaymentHistory> = mutableListOf(),
) : BaseTime() {
  fun isUser() = role == Role.ROLE_USER

  fun isAdmin() = role == Role.ROLE_ADMIN

  fun encodePassword(encodedPassword: String) {
    password = encodedPassword
  }
}
