package com.itstanany.domain.weather.models

sealed class WeatherCondition(
  open val label: String,
) {
  data class Sunny(val description: String = "") : WeatherCondition("Sunny $description")
  data class Rainy(val description: String = "") : WeatherCondition("Rainy $description")
  data class Cloudy(val description: String = "") : WeatherCondition("Cloudy $description")
}
