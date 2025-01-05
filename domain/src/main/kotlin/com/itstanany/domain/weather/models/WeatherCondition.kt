package com.itstanany.domain.weather.models

/**
 * A sealed class representing different weather conditions.
 *
 * This class defines a set of possible weather conditions, such as sunny, rainy, and cloudy.
 * Each condition is represented by a data class that inherits from this sealed class.
 *
 * @param label A short label describing the weather condition.
 */
sealed class WeatherCondition(
  open val label: String,
) {

  /**
   * Represents a sunny weather condition.
   *
   * @param description An optional description of the sunny weather.
   */
  data class Sunny(val description: String = "") : WeatherCondition("Sunny $description")

  /**
   * Represents a rainy weather condition.
   *
   * @param description An optional description of the rainy weather.
   */
  data class Rainy(val description: String = "") : WeatherCondition("Rainy $description")

  /**
   * Represents a cloudy weather condition.
   *
   * @param description An optional description of the cloudy weather.
   */
  data class Cloudy(val description: String = "") : WeatherCondition("Cloudy $description")
}
