package kr.simppl.storage.db.core.domain.post

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.user.User

@Entity
@DiscriminatorValue(Post.INQUIRY)
class Inquiry(
  title: String,
  content: String,
  writer: User,
) : Post(
  title = title,
  content = content,
  writer = writer,
) {
  init {
    require(writer.isUser()) { "1:1 문의사항은 사용자만 작성할 수 있습니다." }
  }
}
