pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenLocal()
    google()
    mavenCentral()
  }
}

rootProject.name = "Weather Now and Later"
include(":app")
include(":core")
include(":data")
include(":features")
include(":features:city-input")
include(":features:current-weather")
include(":features:forecast")
include(":features:splash")
include(":features:no-internet")
include(":domain")
include(":weather-utils")
