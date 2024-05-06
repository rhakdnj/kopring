package kr.simppl.api.auth.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import kr.simppl.api.auth.service.SignUpService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SignUpController(
  private val service: SignUpService,
) {
  @RequestMapping("/users")
  fun signUp(@Valid @RequestBody request: SignUpRequest): ResponseEntity<Long> {
    return ResponseEntity.ok(service.signUp(request))
  }
}

data class SignUpRequest(
  @field:Email(message = "Email is not valid")
  val email: String,
  @field:NotBlank(message = "Password is required")
  val password: String,
  @field:NotBlank(message = "Name is required")
  val name: String,
  @field:NotBlank(message = "Nickname is required")
  val nickname: String,
  @field:Pattern(message = "Date of birth is in yyyyMMdd format", regexp = "^[0-9]{8}$")
  val birth: String,
)
