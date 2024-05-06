package kr.simppl.api.auth.controller

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kr.simppl.api.auth.service.LoginService
import kr.simppl.api.config.properties.AuthProperties
import kr.simppl.api.config.properties.REFRESH_TOKEN
import kr.simppl.shared.common.util.addCookie
import kr.simppl.shared.common.util.deleteCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
  private val authProperties: AuthProperties,
  private val service: LoginService,
) {
  @PostMapping("/users/login")
  fun login(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @Valid @RequestBody loginRequest: LoginRequest,
  ): ResponseEntity<LoginResponse> {
    val (token, refreshToken) = service.login(loginRequest)

    deleteCookie(request, response, REFRESH_TOKEN)
    addCookie(response, REFRESH_TOKEN, refreshToken, authProperties.refreshTokenTimeoutMs / 1000)

    return ResponseEntity.ok(LoginResponse(token, refreshToken))
  }
}

data class LoginRequest(
  @Schema(description = "회원 이메일", example = "test@test.com")
  @field:NotBlank(message = "이메일은 필수값입니다.")
  @field:Email(message = "유효하지 않은 이메일입니다.")
  val email: String,

  @Schema(description = "회원 비밀번호", example = "password")
  @field:NotBlank(message = "비밀번호는 필수값입니다.")
  val password: String,
)

data class LoginResponse(
  @Schema(description = "액세스 토큰")
  val token: String,
  @Schema(description = "리프레쉬 토큰")
  val refreshToken: String,
)
