package com.itstanany.features.current_weather.presentation.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.WeatherCondition

/**
 * Represents the UI state for the Current Weather screen.
 *
 * This data class holds all the necessary state properties required to render the Current Weather screen,
 * including weather data, loading states, and error messages.
 *
 * @property city The city for which the weather data is displayed. Defaults to `null`.
 * @property maxTemp The maximum temperature for the day. Defaults to `0.0`.
 * @property maxTempUnit The unit of measurement for the maximum temperature. Defaults to an empty string.
 * @property minTemp The minimum temperature for the day. Defaults to `0.0`.
 * @property minTempUnit The unit of measurement for the minimum temperature. Defaults to an empty string.
 * @property maxApparentTemp The maximum apparent temperature (feels-like temperature). Defaults to `null`.
 * @property maxApparentTempUnit The unit of measurement for the maximum apparent temperature. Defaults to `null`.
 * @property minApparentTemp The minimum apparent temperature (feels-like temperature). Defaults to `null`.
 * @property minApparentTempUnit The unit of measurement for the minimum apparent temperature. Defaults to `null`.
 * @property maxWindSpeed The maximum wind speed for the day. Defaults to `null`.
 * @property maxWindSpeedUnit The unit of measurement for the maximum wind speed. Defaults to `null`.
 * @property condition The weather condition (e.g., sunny, rainy). Defaults to `null`.
 * @property isLoading Indicates whether weather data is being fetched. Defaults to `false`.
 * @property error The error message to display if fetching weather data fails. Defaults to `null`.
 */
data class CurrentWeatherState(
  val city: City? = null,
  val maxTemp: Double = 0.0,
  val maxTempUnit: String = "",
  val minTemp: Double = 0.0,
  val minTempUnit: String = "",
  val maxApparentTemp: Double? = null,
  val maxApparentTempUnit: String? = null,
  val minApparentTemp: Double? = null,
  val minApparentTempUnit: String? = null,
  val maxWindSpeed: Double? = null,
  val maxWindSpeedUnit: String? = null,
  val condition: WeatherCondition? = null,
  val isLoading: Boolean = false,
  val error: String? = null
)
