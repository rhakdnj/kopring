package kr.simppl.api.auth

import kr.simppl.shared.common.enums.Role

data class TokenPayload(
  val userId: Long,
  val role: Role,
)
