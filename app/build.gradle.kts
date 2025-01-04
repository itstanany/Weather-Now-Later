plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.itstanany.weathernowandlater"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.itstanany.weathernowandlater"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    debug {
      buildConfigField(
        "String",
        "FORECAST_API_URL",
        "\"https://api.open-meteo.com/v1/forecast/\""
      )
      buildConfigField(
        "String",
        "GEOCODING_API_URL",
        "\"https://geocoding-api.open-meteo.com/v1/\""
      )
    }

    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

      buildConfigField(
        "String",
        "FORECAST_API_URL",
        "\"https://api.open-meteo.com/v1/forecast/\""
      )
      buildConfigField(
        "String",
        "GEOCODING_API_URL",
        "\"https://geocoding-api.open-meteo.com/v1/\""
      )
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
    buildConfig = true
  }
}

dependencies {
  implementation(project(":core"))
  implementation(project(":data"))
  implementation(project(":features:city-input"))
  implementation(project(":features:current-weather"))
  implementation(project(":features:forecast"))
  implementation(project(":features:no-internet"))
  implementation(project(":features:splash"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.navigation.compose)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  implementation(libs.hilt.android)
  implementation(libs.hilt.navigation)
  ksp(libs.hilt.compiler)

  implementation(libs.kotlinx.serialization.core)
  implementation(libs.kotlinx.serialization.json)
}