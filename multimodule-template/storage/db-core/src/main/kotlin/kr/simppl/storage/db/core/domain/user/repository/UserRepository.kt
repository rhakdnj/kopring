package kr.simppl.storage.db.core.domain.user.repository

import kr.simppl.storage.db.core.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
  fun findByEmailOrNickname(email: String, nickname: String): User?
  fun findByEmail(email: String): User?
  fun existsByEmail(email: String): Boolean
  fun existsByNickname(nickname: String): Boolean
}
