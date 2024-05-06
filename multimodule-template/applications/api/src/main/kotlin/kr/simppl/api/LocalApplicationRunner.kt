package kr.simppl.api

import kr.simppl.shared.common.enums.Role
import kr.simppl.storage.db.core.domain.user.User
import kr.simppl.storage.db.core.domain.user.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalApplicationRunner(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {
  override fun run(args: ApplicationArguments?) {
    val test = "test"
    val testAdmin = "testAdmin"

    val testUser = User(
      email = "$test@$test.com",
      password = passwordEncoder.encode(test),
      name = test,
      nickname = test,
      birth = "19970711",
      role = Role.ROLE_USER,
    )

    val testAdminUser = User(
      email = "$testAdmin@$test.com",
      password = passwordEncoder.encode(testAdmin),
      name = testAdmin,
      nickname = testAdmin,
      birth = "19970711",
      role = Role.ROLE_ADMIN,
    )

    userRepository.saveAll(listOf(testUser, testAdminUser))
  }
}
