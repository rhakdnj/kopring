import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false

dependencies {
  implementation(project(":storage:db-core"))
  implementation(project(":shared:common"))
  implementation(project(":shared:monitoring"))
  implementation(project(":clients:redis-client"))

  testImplementation(project(":shared:api-docs"))

  implementation(libs.springboot.web)
  implementation(libs.springboot.data.jpa)
  implementation(libs.springboot.validation)
  implementation(libs.springboot.security)
  implementation(libs.bundles.jwt)
  implementation(libs.swagger)
  implementation(libs.logging)

  testImplementation(libs.springboot.test)
}
