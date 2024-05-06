package kr.simppl.api.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@OpenAPIDefinition(
  info = Info(
    title = "심플 Backend API",
    description = "<a href=\"https://simppl.kr\" target=\"_blank\">재외국민 모의지원 서비스 Simppl</a> \n\n Backend API Specification",
    version = "1.0.0",
  ),
  servers = [
    Server(url = "/", description = "Current URL."),
    Server(url = "https://dev-backend.simppl.co.kr/", description = "Simppl Develop URL."),
  ],
)
@Configuration
class SwaggerConfig {
  @Bean
  fun swaggerApi(): OpenAPI = OpenAPI()
    .components(
      Components()
        .addSecuritySchemes(
          HttpHeaders.AUTHORIZATION,
          SecurityScheme()
            .name(HttpHeaders.AUTHORIZATION)
            .type(SecurityScheme.Type.HTTP)
            .scheme("Bearer")
            .bearerFormat("JWT"),
        ),
    )
    .addSecurityItem(SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
}
