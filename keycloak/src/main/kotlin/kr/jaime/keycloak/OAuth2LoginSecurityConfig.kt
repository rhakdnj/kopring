package kr.jaime.keycloak

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.AuthorizationEndpointConfig
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration
@EnableWebSecurity
class OAuth2LoginSecurityConfig(
  private val clientRegistrationRepository: ClientRegistrationRepository
) {
  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .authorizeHttpRequests(Customizer { authorize ->
        authorize
          .requestMatchers("/bugtracker/ui").authenticated()
          .requestMatchers("/bugtracker/ui/admin/**").hasAnyAuthority("SCOPE_bugtracker.admin")
          .requestMatchers("/bugtracker/ui/**").hasAnyAuthority("SCOPE_bugtracker.admin", "SCOPE_bugtracker.user")
          .anyRequest().authenticated()
      })
      .oauth2Login { oauth2 ->
        oauth2.authorizationEndpoint { cfg ->
          cfg.authorizationRequestResolver(
            pkceResolver(clientRegistrationRepository)
          )
        }
      }
      .logout { logout ->
        logout.logoutSuccessHandler(
          oidcLogoutSuccessHandler()
        )
      }

    return http.build()
  }

  private fun oidcLogoutSuccessHandler(): LogoutSuccessHandler {
    val oidcLogoutSuccessHandler =
      OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository)

    // Sets the location that the End-User's User Agent will be redirected to
    // after the logout has been performed at the Provider
    oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/bugtracker/ui")
    return oidcLogoutSuccessHandler
  }

  fun pkceResolver(repo: ClientRegistrationRepository?): OAuth2AuthorizationRequestResolver {
    val resolver = DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization")
    resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce())
    return resolver
  }
}
