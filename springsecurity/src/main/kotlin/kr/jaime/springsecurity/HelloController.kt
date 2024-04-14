package kr.jaime.springsecurity

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class HelloController(
  private val jwtEncoder: JwtEncoder,
) {
  @GetMapping
  fun hello() = "Hello, world!"

  @GetMapping("/csrf")
  fun retrieveCsrfToken(request: HttpServletRequest): CsrfToken {
    return request.getAttribute("_csrf") as CsrfToken
  }

  @PostMapping("/authenticate")
  fun authenticate(authentication: Authentication): Authentication {
    return authentication
  }

  @PostMapping("/jwt")
  fun jwt(authentication: Authentication): JwtResponse {
    return JwtResponse(createToken(authentication))
  }

  private fun createToken(authentication: Authentication): String {
    val claims = JwtClaimsSet.builder()
      .issuer("spring-security")
      .issuedAt(Instant.now())
      .expiresAt(Instant.now().plusSeconds(60))
      .subject(authentication.name)
      .claim("scope", createScope(authentication))
      .build()

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
  }

  private fun createScope(authentication: Authentication): String =
    authentication.authorities.joinToString(" ") { it.authority }
}

data class JwtResponse(
  val token: String,
)
