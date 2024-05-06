package kr.simppl.storage.db.core.domain.post.repository

import kr.simppl.storage.db.core.domain.post.Inquiry
import org.springframework.data.jpa.repository.JpaRepository

interface InquiryRepository : JpaRepository<Inquiry, Long>, FindInquiryQuery
