plugins {
  id("java-library")
  alias(libs.plugins.jetbrains.kotlin.jvm)
  alias(libs.plugins.kotlin.serialization)
  id("jacoco")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
  compilerOptions {
    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
  }
}

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.javax.inject)

  // Testing
  testImplementation(libs.junit.junit)
  testImplementation(libs.kotlinx.coroutines.test)

  // Coroutines
  implementation(libs.jetbrains.kotlinx.coroutines.core)

  // Testing
  testImplementation(libs.jetbrains.kotlinx.coroutines.test)
  testImplementation(libs.turbine)

  // MockK for unit testing
  testImplementation(libs.mockk)

  implementation(libs.kotlinx.serialization.json)
  testImplementation(libs.testng)
}
tasks.withType<Test> {
  useJUnit()
}

tasks.test {
  finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.required.set(false)
    csv.required.set(true)
    html.required.set(true)
  }
}

jacoco {
  toolVersion = "0.8.7"  // Use latest stable version
}