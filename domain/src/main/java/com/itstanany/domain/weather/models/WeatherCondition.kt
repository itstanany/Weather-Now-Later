package com.itstanany.domain.weather.models

sealed class WeatherCondition {
  abstract val description: String
  abstract val code: Int

  data class Sunny private constructor(
    override val description: String,
    override val code: Int
  ) : WeatherCondition() {
    companion object {
      fun fromCode(code: Int): Sunny = when (code) {
        0 -> Sunny("Clear sky", code)
        in 1..3 -> Sunny("Mainly clear", code)
        else -> throw IllegalArgumentException("Invalid sunny weather code: $code")
      }
    }
  }

  data class Rainy private constructor(
    override val description: String,
    override val code: Int
  ) : WeatherCondition() {
    companion object {
      fun fromCode(code: Int): Rainy = when (code) {
        in 51..55 -> createDrizzle(code)
        in 56..57 -> createFreezingDrizzle(code)
        in 61..65 -> createRain(code)
        in 66..67 -> createFreezingRain(code)
        in 80..82 -> createRainShowers(code)
        else -> throw IllegalArgumentException("Invalid rainy weather code: $code")
      }

      private fun createDrizzle(code: Int) = Rainy(
        "Drizzle: ${
          when (code) {
            51 -> "Light"
            53 -> "Moderate"
            55 -> "Dense"
            else -> throw IllegalArgumentException("Invalid drizzle code")
          }
        } intensity",
        code
      )

      private fun createFreezingDrizzle(code: Int) = Rainy(
        "Freezing Drizzle: ${if (code == 56) "Light" else "Dense"} intensity",
        code
      )

      private fun createRain(code: Int) = Rainy(
        "Rain: ${
          when (code) {
            61 -> "Slight"
            63 -> "Moderate"
            65 -> "Heavy"
            else -> throw IllegalArgumentException("Invalid rain code")
          }
        } intensity",
        code
      )

      private fun createFreezingRain(code: Int) = Rainy(
        "Freezing Rain: ${if (code == 66) "Light" else "Heavy"} intensity",
        code
      )

      private fun createRainShowers(code: Int) = Rainy(
        "Rain showers: ${
          when (code) {
            80 -> "Slight"
            81 -> "Moderate"
            82 -> "Violent"
            else -> throw IllegalArgumentException("Invalid rain showers code")
          }
        }",
        code
      )
    }
  }

  data class Cloudy private constructor(
    override val description: String,
    override val code: Int
  ) : WeatherCondition() {
    companion object {
      fun fromCode(code: Int): Cloudy = when (code) {
        45, 48 -> createFog(code)
        in 71..75 -> createSnowfall(code)
        77 -> Cloudy("Snow grains", code)
        85, 86 -> createSnowShowers(code)
        95 -> Cloudy("Thunderstorm: Slight or moderate", code)
        96, 99 -> createThunderstorm(code)
        else -> throw IllegalArgumentException("Invalid cloudy weather code: $code")
      }

      private fun createFog(code: Int) = Cloudy(
        if (code == 45) "Fog" else "Depositing rime fog",
        code
      )

      private fun createSnowfall(code: Int) = Cloudy(
        "Snow fall: ${
          when (code) {
            71 -> "Slight"
            73 -> "Moderate"
            75 -> "Heavy"
            else -> throw IllegalArgumentException("Invalid snowfall code")
          }
        } intensity",
        code
      )

      private fun createSnowShowers(code: Int) = Cloudy(
        "Snow showers ${if (code == 85) "slight" else "heavy"}",
        code
      )

      private fun createThunderstorm(code: Int) = Cloudy(
        "Thunderstorm with ${if (code == 96) "slight" else "heavy"} hail",
        code
      )
    }
  }

  companion object {
    fun fromCode(code: Int): WeatherCondition = when (code) {
      in 0..3 -> Sunny.fromCode(code)
      in 51..57, in 61..67, in 80..82 -> Rainy.fromCode(code)
      45, 48, in 71..77, in 85..86, in 95..99 -> Cloudy.fromCode(code)
      else -> throw IllegalArgumentException("Unknown weather code: $code")
    }
  }
}
