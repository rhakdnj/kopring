package kr.simppl.api.auth.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.simppl.api.auth.AuthTokenProvider
import kr.simppl.shared.common.util.getAccessToken
import kr.simppl.shared.common.util.writeHttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class TokenAuthenticationFilter(
  private val tokenProvider: AuthTokenProvider,
) : OncePerRequestFilter() {

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val tokenStr = request.getAccessToken()

    tokenStr ?: run {
      filterChain.doFilter(request, response)
      return
    }

    val token = tokenProvider.convertAuthToken(tokenStr)

    if (token.isValid) {
      val authentication = tokenProvider.getAuthentication(token)
      SecurityContextHolder.getContext().authentication = authentication
    } else {
      writeHttpServletResponse(
        response = response,
        status = HttpStatus.UNAUTHORIZED,
        message = "올바르지 않은 Token입니다.",
      )
      return
    }

    filterChain.doFilter(request, response)
  }
}
