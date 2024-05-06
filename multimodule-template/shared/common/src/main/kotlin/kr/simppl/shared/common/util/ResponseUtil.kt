package kr.simppl.shared.common.util

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import kr.simppl.shared.common.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import kotlin.text.Charsets.UTF_8

fun writeHttpServletResponse(response: HttpServletResponse, status: HttpStatus, message: String) {
  response.contentType = MediaType.APPLICATION_JSON_VALUE
  response.characterEncoding = UTF_8.name()
  response.status = HttpStatus.UNAUTHORIZED.value()

  ObjectMapper().writeValue(
    response.outputStream,
    ErrorResponse(message),
  )
}
