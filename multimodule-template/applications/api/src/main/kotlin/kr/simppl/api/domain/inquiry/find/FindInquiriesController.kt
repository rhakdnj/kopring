package kr.simppl.api.domain.inquiry.find

import jakarta.validation.constraints.NotBlank
import kr.simppl.api.auth.LoginUser
import kr.simppl.api.auth.TokenPayload
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/inquiries")
class FindInquiriesController(
  private val service: FindInquiriesService,
) {
  @GetMapping
  fun find(
    @LoginUser tokenPayload: TokenPayload,
    @PageableDefault(
      size = 10,
      sort = ["createdAt"],
      direction = Sort.Direction.DESC,
    ) pageable: Pageable,
  ): ResponseEntity<Unit> {
    service.find(pageable, tokenPayload)
    return ResponseEntity.ok().build()
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
