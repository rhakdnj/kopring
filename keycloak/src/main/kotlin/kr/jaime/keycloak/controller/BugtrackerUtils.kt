package kr.jaime.keycloak.controller

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.tomcat.util.codec.binary.Base64

fun prettyBody(accessToken: String): String {
  try {
    val json = decodeJSONBody(accessToken)

    val mapper = ObjectMapper()

    // Use indentation of 4 spaces
    val indenter: DefaultPrettyPrinter.Indenter =
      DefaultIndenter("    ", DefaultIndenter.SYS_LF)
    val printer = DefaultPrettyPrinter()
    printer.indentObjectsWith(indenter)
    printer.indentArraysWith(indenter)

    // Format the JSON
    val tokenBodyMap: Map<*, *> = mapper.readValue(
      json,
      MutableMap::class.java
    )
    val prettyJson = mapper.writer(printer).writeValueAsString(tokenBodyMap)
    return prettyJson
  } catch (e: Exception) {
    // If token is not a JWT

    return accessToken
  }
}

fun decodeJSONBody(accessToken: String): String {
  // extract token body. Will throw exception if not a JWT

  val tokenBody = accessToken.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
  val json = String(Base64.decodeBase64URLSafe(tokenBody))
  return json
}
