package com.itstanany.core.navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {
  @Serializable
  data object Splash : NavRoutes()

  @Serializable
  data object CityInput : NavRoutes()

  @Serializable
  data object CurrentWeather : NavRoutes()

  @Serializable
  data object NoInternet : NavRoutes()

  @Serializable
  data object Forecast : NavRoutes()
}
