package kr.simppl.shared.common.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

const val TOKEN_PREFIX = "Bearer "

fun HttpServletRequest.getAccessToken(): String? = this.getHeader(HttpHeaders.AUTHORIZATION)
  .takeIf { it?.startsWith(TOKEN_PREFIX, true) ?: false }?.substring(TOKEN_PREFIX.length)
