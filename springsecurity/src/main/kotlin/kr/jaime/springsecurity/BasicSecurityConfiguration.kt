package kr.jaime.springsecurity

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class BasicSecurityConfiguration {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.authorizeHttpRequests { auth ->
      auth.anyRequest().authenticated()
    }
    http.sessionManagement { session ->
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
    // http.formLogin(Customizer.withDefaults())
    http.httpBasic(Customizer.withDefaults())

    http.csrf{
      it.disable()
    }
    return http.build() as SecurityFilterChain
  }
}
