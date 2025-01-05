package com.itstanany.domain.weather.models

import java.time.LocalDate

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
