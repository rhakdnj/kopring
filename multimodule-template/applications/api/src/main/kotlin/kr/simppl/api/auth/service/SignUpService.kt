package kr.simppl.api.auth.service

import kr.simppl.api.auth.controller.SignUpRequest
import kr.simppl.api.auth.converter.SignUpRequestToUserConverter
import kr.simppl.api.domain.user.domainservice.UserService
import kr.simppl.storage.db.core.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface SignUpService {
  fun signUp(request: SignUpRequest): Long
}

@Service
class SignUpServiceImpl(
  private val userService: UserService,
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
  private val converter: SignUpRequestToUserConverter,
) : SignUpService {
  override fun signUp(request: SignUpRequest): Long {
    userService.checkDuplicatedEmailOrNickname(request.email, request.nickname)

    val user = converter.convert(request)
    user.encodePassword(passwordEncoder.encode(user.password))

    return userRepository.save(user).id!!
  }
}
