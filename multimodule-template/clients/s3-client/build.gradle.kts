dependencies {
  // https://github.com/awspring/spring-cloud-aws?tab=readme-ov-file#compatibility-with-spring-project-versions
  implementation(platform(libs.springboot.s3.sub))
  implementation(libs.springboot.s3)

  implementation(libs.springboot.web)
}
