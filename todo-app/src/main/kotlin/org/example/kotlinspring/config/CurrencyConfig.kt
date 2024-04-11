package org.example.kotlinspring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "currency")
class CurrencyConfig {
  lateinit var url: String
  lateinit var username: String
  lateinit var key: String
}
