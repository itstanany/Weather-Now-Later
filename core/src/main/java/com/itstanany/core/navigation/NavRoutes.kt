package com.itstanany.core.navigation

import kotlinx.serialization.Serializable

/**
 * Represents the possible navigation routes within the application.
 *
 * This sealed class defines all the screens that can be navigated to within the app.
 * Each object within the sealed class represents a specific screen or destination.
 */
sealed class NavRoutes {

  /**
   * Represents the splash screen.
   */
  @Serializable
  data object Splash : NavRoutes()

  /**
   * Represents the city input screen.
   */
  @Serializable
  data object CityInput : NavRoutes()

  /**
   * Represents the current weather screen.
   */
  @Serializable
  data object CurrentWeather : NavRoutes()

  /**
   * Represents the no internet connection screen.
   */
  @Serializable
  data object NoInternet : NavRoutes()

  /**
   * Represents the weather forecast screen.
   */
  @Serializable
  data object Forecast : NavRoutes()
}