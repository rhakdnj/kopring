allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

dependencies {
  implementation(project(":shared:common"))
  implementation(libs.springboot.data.jpa)
  runtimeOnly(libs.postgresql)

  implementation("com.querydsl:querydsl-jpa:${rootProject.libs.versions.querydsl.get()}:jakarta")
  kapt("com.querydsl:querydsl-apt:${rootProject.libs.versions.querydsl.get()}:jakarta")
}
