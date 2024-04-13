package kr.jaime.springsecurity

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
  @GetMapping
  fun hello() = "Hello, world!"

  @GetMapping("/csrf")
  fun retrieveCsrfToken(request: HttpServletRequest): CsrfToken {
    return request.getAttribute("_csrf") as CsrfToken
  }
}
