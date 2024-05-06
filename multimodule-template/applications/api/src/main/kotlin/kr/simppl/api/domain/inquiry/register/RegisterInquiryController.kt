package kr.simppl.api.domain.inquiry.register

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import kr.simppl.api.auth.LoginUser
import kr.simppl.api.auth.TokenPayload
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/inquiries")
class RegisterInquiryController(
  private val inquiryService: RegisterInquiryService,
) {
  @PostMapping
  fun register(@LoginUser tokenPayload: TokenPayload, @Valid @RequestBody registerRequest: InquiryRegisterRequest): ResponseEntity<Long> {
    return ResponseEntity.ok(
      inquiryService.register(registerRequest, tokenPayload.userId),
    )
  }
}

data class InquiryRegisterRequest(
  @field:NotBlank(message = "제목은 필수값입니다.")
  val title: String,
  @field:NotBlank(message = "내용은 필수값입니다.")
  val content: String,
) {
  init {
    require(title.length <= 100) { "제목은 1000자 이하로 입력해주세요." }
  }
}
