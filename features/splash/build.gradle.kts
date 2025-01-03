plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.itstanany.features.splash"
  compileSdk = 35

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }

  buildFeatures {
    compose = true
  }
}

dependencies {

  implementation(project(":domain"))
  implementation(libs.androidx.core.splashscreen)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  implementation(libs.kotlinx.serialization.core)
  implementation(libs.kotlinx.serialization.json)

  // Compose BOM
  implementation(platform(libs.androidx.compose.bom)) // Matches "androidx-compose-bom" in libs.versions.toml

  // Compose dependencies (no version needed, managed by BOM)
  implementation(libs.androidx.activity.compose) // Matches "androidx-activity-compose" in libs.versions.toml
  implementation(libs.androidx.material3) // Matches "androidx-material3" in libs.versions.toml
  implementation(libs.androidx.ui) // Matches "androidx-ui" in libs.versions.toml
  implementation(libs.androidx.ui.tooling.preview) // Matches "androidx-ui-tooling-preview" in libs.versions.toml
  debugImplementation(libs.androidx.ui.tooling) // Matches "androidx-ui-tooling" in libs.versions.toml
  debugImplementation(libs.androidx.ui.test.manifest) // Matches "androidx-ui-test-manifest" in libs.versions.toml

  // Testing dependencies
  testImplementation(libs.junit) // Matches "junit" in libs.versions.toml
  androidTestImplementation(platform(libs.androidx.compose.bom)) // Matches "androidx-compose-bom" in libs.versions.toml
  androidTestImplementation(libs.androidx.ui.test.junit4) // Matches "androidx-ui-test-junit4" in libs.versions.toml

}