package kr.simppl.storage.db.core.domain.post.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.simppl.shared.common.enums.Role
import kr.simppl.storage.db.core.domain.post.Inquiry
import kr.simppl.storage.db.core.domain.post.QInquiry.inquiry
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

interface FindInquiryQuery {
  fun find(pageable: Pageable, userId: Long, role: Role): Pair<Int, List<Inquiry>>
}

@Repository
class FindInquiryQueryImpl(
  private val jpaQueryFactory: JPAQueryFactory,
) : FindInquiryQuery {
  override fun find(pageable: Pageable, userId: Long, role: Role): Pair<Int, List<Inquiry>> {
    val predicate = BooleanBuilder()
    predicate.and(inquiry.deletedAt.isNull)

    if (role == Role.ROLE_USER) {
      predicate.and(inquiry.writer.id.eq(userId))
    }

    val totalCount = jpaQueryFactory.select(inquiry.id)
      .from(inquiry)
      .where(predicate)
      .fetch()
      .size

    val result = jpaQueryFactory
      .selectFrom(inquiry)
      .join(inquiry.writer)
      .fetchJoin()
      .where(predicate)
      .offset(pageable.offset)
      .limit(pageable.pageSize.toLong())
      .fetch()

    return totalCount to result
  }
}
