package kr.simppl.storage.db.core.domain.post

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.BaseTime
import kr.simppl.storage.db.core.domain.user.User
import org.hibernate.annotations.SQLDelete

@Table(name = "Post")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@SQLDelete(sql = "UPDATE Post SET deleted_at = NOW() WHERE id = ?")
abstract class Post(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "title", nullable = false)
  var title: String,

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  var content: String,

  @Embedded
  val postLike: PostLike = PostLike(),

  @Column(name = "view_count", nullable = false)
  var viewCount: Int = 0,

  @Column(name = "is_fixed", nullable = true)
  var isFixed: Boolean = false,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val writer: User,
) : BaseTime() {
  fun view() {
    viewCount++
  }

  fun like() {
    postLike.likeCount++
  }

  fun hate() {
    postLike.hateCount++
  }

  companion object {
    const val NOTICE = "NOTICE"
    const val FAQ = "FAQ"
    const val INQUIRY = "INQUIRY"
    const val USER = "USER"
  }
}

@Embeddable
data class PostLike(
  @Column(name = "like_count", nullable = false)
  var likeCount: Int = 0,
  @Column(name = "hate_count", nullable = false)
  var hateCount: Int = 0,
) {
  init {
    require(likeCount >= 0) { "좋아요 수는 0 이상이어야 합니다." }
    require(hateCount >= 0) { "싫어요 수는 0 이상이어야 합니다." }
  }
}
