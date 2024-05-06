dependencies {
  implementation(libs.springboot.web)
  compileOnly(libs.springboot.security)
  compileOnly(libs.springboot.data.jpa)
  implementation(libs.bundles.jwt)
  implementation(libs.logging)
}
