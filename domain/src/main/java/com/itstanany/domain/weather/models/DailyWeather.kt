package com.itstanany.domain.weather.models

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
)
