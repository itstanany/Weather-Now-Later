package com.itstanany.domain.weather.models

import java.time.LocalDate

/**
 * Represents the weather information for a single day.
 *
 * This data class holds various weather parameters like temperature, apparent temperature,
 * wind speed, and weather condition for a specific date.
 *
 * @property maxTemp The maximum temperature for the day.
 * @property maxTempUnit The unit of measurement for the maximum temperature (e.g., "°C", "°F").
 * @property minTemp The minimum temperature for the day.
 * @property minTempUnit The unit of measurement for the minimum temperature (e.g., "°C", "°F").
 * @property maxApparentTemp The maximum apparent temperature for the day (optional).
 * @property maxApparentTempUnit The unit of measurement for the maximum apparent temperature (optional).
 * @property minApparentTemp The minimum apparent temperature for the day (optional).
 * @property minApparentTempUnit The unit of measurement for the minimum apparent temperature (optional).
 * @property condition The weather condition for the day (e.g., sunny, rainy, cloudy).
 * @property maxWindSpeed The maximum wind speed for the day (optional).
 * @property maxWindSpeedUnit The unit of measurement for the maximum wind speed (optional).
 * @property date The date for which the weather information is provided.
 */
data class DailyWeather(
  val maxTemp: Double,
  val maxTempUnit: String,
  val minTemp: Double,
  val minTempUnit: String,
  val maxApparentTemp: Double?,
  val maxApparentTempUnit: String?,
  val minApparentTemp: Double?,
  val minApparentTempUnit: String?,
  val condition: WeatherCondition,
  val maxWindSpeed: Double?,
  val maxWindSpeedUnit: String?,
  val date: LocalDate
)
