package kr.simppl.storage.db.core.domain.post

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import kr.simppl.storage.db.core.domain.user.User

@Entity
@DiscriminatorValue(Post.NOTICE)
class Notice(
  title: String,
  content: String,
  writer: User,
  isFixed: Boolean = false,
) : Post(
  title = title,
  content = content,
  writer = writer,
  isFixed = isFixed,
) {
  init {
    require(writer.isAdmin()) { "공지사항은 관리자만 작성할 수 있습니다." }
  }
}
