plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  id("maven-publish")
}

android {
  namespace = "com.itstanany.weathernowandlater.weather_utils"
  compileSdk = 34

  defaultConfig {
    minSdk = 26

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

  publishing {
    singleVariant("release") {
      withSourcesJar()
      withJavadocJar()
    }
  }
}


publishing {
  publications {
    register<MavenPublication>("release") {
      groupId = "com.itstanany.weathernowandlater"
      artifactId = "weather-utils"
      version = "1.0.0"

      afterEvaluate {
        from(components["release"])
      }
    }
  }
}

dependencies {
  implementation(project(":core"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}

tasks.register("publishToLocalMaven") {
  dependsOn("publishReleasePublicationToMavenLocal")
}