package kr.simppl.api.domain.inquiry.register

import kr.simppl.shared.common.exception.NotFoundException
import kr.simppl.storage.db.core.domain.post.Inquiry
import kr.simppl.storage.db.core.domain.post.repository.InquiryRepository
import kr.simppl.storage.db.core.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

interface RegisterInquiryService {
  fun register(req: InquiryRegisterRequest, userId: Long): Long
}

@Service
class RegisterInquiryServiceImpl(
  private val inquiryRepository: InquiryRepository,
  private val userRepository: UserRepository,
) : RegisterInquiryService {
  override fun register(req: InquiryRegisterRequest, userId: Long): Long {
    val user = userRepository.findByIdOrNull(userId)
      ?: throw NotFoundException("사용자를 찾을 수 없습니다.")

    return this.inquiryRepository.save(
      Inquiry(
        title = req.title,
        content = req.content,
        writer = user,
      ),
    ).id!!
  }
}
