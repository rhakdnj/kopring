package kr.simppl.api.domain.user.domainservice

import kr.simppl.shared.common.exception.ConflictException
import kr.simppl.storage.db.core.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
  private val userRepository: UserRepository,
) {
  fun checkDuplicatedEmailOrNickname(email: String, nickname: String) {
    userRepository.findByEmailOrNickname(email, nickname)?.let {
      if (it.email == email) {
        throw ConflictException("${email}은 현재 사용중입니다.")
      }
      throw ConflictException("${nickname}은 현재 사용중입니다.")
    }
  }

  fun checkDuplicatedEmail(email: String) {
    if (userRepository.existsByEmail(email)) {
      throw ConflictException("${email}은 현재 사용중입니다.")
    }
  }

  fun checkDuplicatedNickname(nickname: String) {
    if (userRepository.existsByNickname(nickname)) {
      throw ConflictException("${nickname}은 현재 사용중입니다.")
    }
  }
}
