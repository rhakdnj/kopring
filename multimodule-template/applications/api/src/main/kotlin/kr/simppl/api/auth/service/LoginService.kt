package kr.simppl.api.auth.service

import kr.simppl.api.auth.AuthTokenProvider
import kr.simppl.api.auth.controller.LoginRequest
import kr.simppl.api.config.properties.AuthProperties
import kr.simppl.clients.redis.client.repository.RefreshTokenRepository
import kr.simppl.shared.common.exception.UnauthorizedException
import kr.simppl.storage.db.core.domain.user.User
import kr.simppl.storage.db.core.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Date

interface LoginService {
  fun login(request: LoginRequest): Pair<String, String>
}

@Service
class LoginServiceImpl(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
  private val authProperties: AuthProperties,
  private val authTokenProvider: AuthTokenProvider,
  private val refreshTokenRepository: RefreshTokenRepository,
) : LoginService {
  override fun login(request: LoginRequest): Pair<String, String> {
    val email = request.email
    val rawPassword = request.password

    val findUser = userRepository.findByEmail(request.email)

    if (findUser == null || !passwordEncoder.matches(rawPassword, findUser.password)) {
      throw UnauthorizedException("이메일 또는 비밀번호를 잘못 입력했습니다.")
    }

    return createTokens(findUser)
  }

  private fun createTokens(findUser: User): Pair<String, String> {
    val role = findUser.role
    val now = Date()

    val (_, tokenTimeoutMs, refreshTokenTimeoutMs) = authProperties

    val token = authTokenProvider.createAuthToken(
      findUser.id!!,
      Date(now.time + tokenTimeoutMs),
      role.name,
    ).token

    val refreshToken = authTokenProvider.createAuthToken(
      findUser.id!!,
      Date(now.time + refreshTokenTimeoutMs),
    ).token

    refreshTokenRepository.save(findUser.id!!, refreshToken, refreshTokenTimeoutMs)

    return token to refreshToken
  }
}
