package kr.simppl.api.domain.inquiry.find

import kr.simppl.api.auth.TokenPayload
import kr.simppl.storage.db.core.domain.post.repository.InquiryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

interface FindInquiriesService {
  fun find(pageable: Pageable, tokenPayload: TokenPayload)
}

@Service
class FindInquiriesServiceImpl(
  private val inquiryRepository: InquiryRepository,
) : FindInquiriesService {
  override fun find(pageable: Pageable, tokenPayload: TokenPayload) {
    val (totalCount, result) = inquiryRepository.find(pageable, tokenPayload.userId, tokenPayload.role)

    println("totalCount: $totalCount")
    println("result: $result")
  }
}
