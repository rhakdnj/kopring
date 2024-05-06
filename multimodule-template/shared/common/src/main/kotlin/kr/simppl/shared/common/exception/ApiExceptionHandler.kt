package kr.simppl.shared.common.exception

import kr.simppl.shared.common.dto.response.BadRequestResponse
import kr.simppl.shared.common.dto.response.ErrorResponse
import kr.simppl.shared.common.logging.Logger.Companion.log
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

const val BAD_REQUEST = "올바르지 않은 요청입니다."
const val INTERNAL_SERVER_ERROR = "서버 내부 요류입니다."

@RestControllerAdvice(basePackages = ["kr.simppl"])
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
  @ExceptionHandler(value = [HttpException::class])
  protected fun handleApiException(ex: HttpException): ResponseEntity<ErrorResponse> {
    log.info { ex.message }
    return ResponseEntity.status(ex.status).body(ErrorResponse(ex.message ?: INTERNAL_SERVER_ERROR))
  }

  override fun handleHttpMessageNotReadable(
    ex: HttpMessageNotReadableException,
    headers: HttpHeaders,
    status: HttpStatusCode,
    request: WebRequest,
  ): ResponseEntity<Any>? {
    log.info { "${ex.message}" }
    val errorResponse = ErrorResponse(ex.message ?: BAD_REQUEST)
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
  }

  override fun handleMethodArgumentNotValid(
    ex: MethodArgumentNotValidException,
    headers: HttpHeaders,
    status: HttpStatusCode,
    request: WebRequest,
  ): ResponseEntity<Any>? {
    val errorResponse = BadRequestResponse(BAD_REQUEST)
    ex.bindingResult.fieldErrors.forEach {
      errorResponse.addValidation(it.field, it.defaultMessage!!)
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
  }

  @ExceptionHandler(value = [IllegalArgumentException::class])
  protected fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
    log.info { ex.message }
    val errorResponse = ErrorResponse(ex.message ?: BAD_REQUEST)
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
  }

  @ExceptionHandler(value = [AccessDeniedException::class])
  protected fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ErrorResponse> {
    log.info { ex.message }
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
      .body(ErrorResponse("해당 API에 대한 권한이 없습니다."))
  }

  @ExceptionHandler(value = [Exception::class])
  protected fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
    log.error(ex) { ex.message }
    val errorResponse = ErrorResponse(ex.message ?: INTERNAL_SERVER_ERROR)
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
  }
}
