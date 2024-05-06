import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kapt)
  alias(libs.plugins.kotlin.plugin.spring) apply false
  alias(libs.plugins.kotlin.plugin.jpa) apply false
  alias(libs.plugins.springboot) apply false
  alias(libs.plugins.springboot.denpendency.management)
  alias(libs.plugins.asciidoctor) apply false
  alias(libs.plugins.ktlint) apply false
}

java.sourceCompatibility = JavaVersion.valueOf("VERSION_${libs.versions.java.get()}")

allprojects {
  group = rootProject.libs.versions.group.get()

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.jetbrains.kotlin.kapt")
    plugin("org.jetbrains.kotlin.plugin.spring")
    plugin("org.jetbrains.kotlin.plugin.jpa")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("org.asciidoctor.jvm.convert")
    plugin("org.jlleitschuh.gradle.ktlint")
  }

  dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
  }

  tasks.getByName("bootJar") {
    enabled = false
  }

  tasks.getByName("jar") {
    enabled = true
  }

  java.sourceCompatibility = JavaVersion.valueOf("VERSION_${rootProject.libs.versions.java.get()}")
  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = rootProject.libs.versions.java.get()
    }
  }

  tasks.test {
    useJUnitPlatform {
      excludeTags("develop", "restdocs")
    }
  }

  tasks.register<Test>("unitTest") {
    group = "verification"
    useJUnitPlatform {
      excludeTags("develop", "context", "restdocs")
    }
  }

  tasks.register<Test>("contextTest") {
    group = "verification"
    useJUnitPlatform {
      includeTags("context")
    }
  }

  tasks.register<Test>("restDocsTest") {
    group = "verification"
    useJUnitPlatform {
      includeTags("restdocs")
    }
  }

  tasks.register<Test>("developTest") {
    group = "verification"
    useJUnitPlatform {
      includeTags("develop")
    }
  }

  tasks.getByName("asciidoctor") {
    dependsOn("restDocsTest")
  }
}
