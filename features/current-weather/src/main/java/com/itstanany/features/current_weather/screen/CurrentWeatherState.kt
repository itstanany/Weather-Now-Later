package com.itstanany.features.current_weather.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.WeatherCondition

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
