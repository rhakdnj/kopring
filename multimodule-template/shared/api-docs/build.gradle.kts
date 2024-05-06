dependencies {
  compileOnly(libs.servlet.api)
  compileOnly(libs.springboot.test)
  api(libs.spring.restdocs.mockmvc)
  api(libs.spring.restdocs.restassured)
  api(libs.rest.assured.spring.mock.mvc)
}
