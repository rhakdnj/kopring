package kr.jaime.springsecurity

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.sql.DataSource


@Configuration
class JwtSecurityConfiguration {
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

    http.csrf {
      it.disable()
    }

    http.headers { header ->
      header.frameOptions { frameOption ->
        frameOption.sameOrigin()
      }
    }

    http.oauth2ResourceServer { oauth2 ->
      oauth2.jwt(Customizer.withDefaults())
    }

    return http.build() as SecurityFilterChain
  }

  @Bean
  fun keyPair(): KeyPair {
    KeyPairGenerator.getInstance("RSA").apply {
      initialize(2048)
    }.run {
      return generateKeyPair()
    }
  }

  @Bean
  fun rsaKey(keyPair: KeyPair): RSAKey {
    return RSAKey
        .Builder(keyPair.public as RSAPublicKey)
        .privateKey(keyPair().private)
        .keyID(UUID.randomUUID().toString())
        .build()
  }

  @Bean
  fun jwkSource(rsaKey: RSAKey): JWKSource<SecurityContext> {
    val jwkSet = JWKSet(rsaKey)
    return JWKSource { jwkSelector, _ -> jwkSelector.select(jwkSet) }
  }

  @Bean
  fun jwtDecoder(rsaKey: RSAKey): JwtDecoder {
    return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build()
  }

  @Bean
  fun jwtEncoder(jwtSource: JWKSource<SecurityContext>) = NimbusJwtEncoder(jwtSource)

  @Bean
  fun corsConfigurer(): WebMvcConfigurer {
    return object : WebMvcConfigurer {
      override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("http://localhost:3000")
      }
    }
  }

  @Bean
  fun dataSource(): DataSource {
    return EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts(DEFAULT_USER_SCHEMA_DDL_LOCATION)
        .build()
  }

  @Bean
  fun userDetailsService(dataSource: DataSource): UserDetailsService {
    val user = User.withUsername("user")
        .password("user")
        .roles("USER")
        .passwordEncoder { passwordEncoder().encode(it) }
        .build()

    val admin = User.withUsername("admin")
        .password("admin")
        .roles("USER", "ADMIN")
        .passwordEncoder { passwordEncoder().encode(it) }
        .build()

    val jdbcUserDetailsManager = JdbcUserDetailsManager(dataSource)
    jdbcUserDetailsManager.createUser(user)
    jdbcUserDetailsManager.createUser(admin)
    return jdbcUserDetailsManager
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }
}
