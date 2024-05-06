package kr.simppl.clients.redis.client.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepository(
  private val redisTemplate: StringRedisTemplate,
) {
  fun findOne(userId: Long): String? {
    return redisTemplate.opsForValue().get(userId)
  }

  fun save(userId: Long, refreshToken: String, refreshTokenTimeoutMs: Long) {
    redisTemplate.opsForValue().set(userId.toString(), refreshToken, refreshTokenTimeoutMs, TimeUnit.MILLISECONDS)
  }
}
