package kr.simppl.api.auth

import io.jsonwebtoken.security.Keys
import kr.simppl.api.config.properties.AuthProperties
import kr.simppl.shared.common.exception.UnauthorizedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class AuthTokenProvider(
  authProperties: AuthProperties,
) {
  private val key: SecretKey = Keys.hmacShaKeyFor(authProperties.tokenSecret.toByteArray())

  fun createAuthToken(userId: Long, expiry: Date, role: String? = null): AuthToken {
    return AuthToken(userId, expiry, key, role)
  }

  fun convertAuthToken(token: String): AuthToken {
    return AuthToken(token, key)
  }

  fun getAuthentication(authToken: AuthToken): Authentication {
    if (authToken.isValid) {
      val claims = authToken.tokenClaims
      val authorities = arrayOf(claims!![AUTHORITIES_KEY].toString())
        .map(::SimpleGrantedAuthority)
        .toList()
      return UsernamePasswordAuthenticationToken(User(claims.subject, "", authorities), authToken, authorities)
    }

    throw UnauthorizedException("올바르지 않은 Token입니다.")
  }
}
