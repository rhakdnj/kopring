package org.example.kotlinspring.exception

import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

  companion object : KLogging()

  override fun handleMethodArgumentNotValid(
    ex: MethodArgumentNotValidException,
    headers: HttpHeaders,
    status: HttpStatusCode,
    request: WebRequest
  ): ResponseEntity<Any> {
    logger.error("MethodArgumentNotValidException: ${ex.message}", ex)

    val errors = ex.bindingResult.allErrors
      .map { it.defaultMessage!! }
      .sorted()

    return ResponseEntity.badRequest()
      .body(errors.joinToString(", ") { it })
  }


  @ExceptionHandler(Exception::class)
  fun handleServerException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
    logger.error("Exception observed: ${ex.message}", ex)

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(ex.message ?: "Internal server error")
  }
}
