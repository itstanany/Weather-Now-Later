plugins {
  id("java-library")
  alias(libs.plugins.jetbrains.kotlin.jvm)
  alias(libs.plugins.kotlin.serialization)
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
  testImplementation(libs.mockito.kotlin)
  testImplementation(libs.kotlinx.coroutines.test)

  // Coroutines
  implementation(libs.jetbrains.kotlinx.coroutines.core)

  // Testing
  testImplementation(libs.jetbrains.kotlinx.coroutines.test)

  implementation(libs.kotlinx.serialization.json)
}
