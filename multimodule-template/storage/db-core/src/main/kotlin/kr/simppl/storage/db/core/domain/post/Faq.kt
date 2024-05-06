package kr.simppl.storage.db.core.domain.post

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.user.User

@Entity
@DiscriminatorValue(Post.FAQ)
class Faq(
  question: String,
  answer: String,
  writer: User,
) : Post(
  title = question,
  content = answer,
  writer = writer,
) {
  init {
    require(writer.isAdmin()) { "FAQ는 관리자만 작성할 수 있습니다." }
  }
}
