package kr.simppl.api.auth.controller

import kr.simppl.api.auth.LoginUser
import kr.simppl.api.auth.TokenPayload
import kr.simppl.api.auth.annotation.AuthorizedAdmin
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
  @AuthorizedAdmin
  @GetMapping("/test")
  fun login(@LoginUser tokenPayload: TokenPayload, @Param("hello") hello: String) {
    println(tokenPayload.userId)
    println(tokenPayload.role)
  }
}
