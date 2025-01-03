package com.itstanany.domain.weather.models

data class DailyWeather(
  val maxTemp: Float,
  val minTemp: Float,
  val maxApparentTemp: Float,
  val minApparentTemp: Float,
  val condition: WeatherCondition,
  val maxWindSpeed: Float,
)
