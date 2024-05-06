package kr.simppl.api.auth.converter

import kr.simppl.api.auth.controller.SignUpRequest
import kr.simppl.storage.db.core.domain.user.User
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class SignUpRequestToUserConverter : Converter<SignUpRequest, User> {
  override fun convert(source: SignUpRequest): User {
    return source.run {
      User(
        email = email,
        password = password,
        name = name,
        nickname = nickname,
        birth = birth,
      )
    }
  }
}
