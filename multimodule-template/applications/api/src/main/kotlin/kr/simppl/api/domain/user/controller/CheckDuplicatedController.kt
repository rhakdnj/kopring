package kr.simppl.api.domain.user.controller

import kr.simppl.api.domain.user.domainservice.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CheckDuplicatedController(
  private val userService: UserService,
) {
  @PostMapping("/users/duplicate-email")
  fun check(@RequestBody request: CheckDuplicatedEmailRequest): ResponseEntity<Unit> {
    userService.checkDuplicatedEmail(request.email)
    return ResponseEntity.noContent().build()
  }

  @PostMapping("/users/duplicate-nickname")
  fun check(@RequestBody request: CheckDuplicatedNicknameRequest): ResponseEntity<Unit> {
    userService.checkDuplicatedNickname(request.nickname)
    return ResponseEntity.noContent().build()
  }
}

data class CheckDuplicatedEmailRequest(
  val email: String,
)

data class CheckDuplicatedNicknameRequest(
  val nickname: String,
)
