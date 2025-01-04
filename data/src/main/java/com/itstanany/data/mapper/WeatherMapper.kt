package com.itstanany.data.mapper

import com.itstanany.data.weather.models.WeatherCondition
import com.itstanany.domain.weather.models.DailyWeather
import java.time.LocalDate
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
  companion object {
    fun mapToDomain(
      weatherCode: Int,
      maxTemp: Double,
      minTemp: Double,
      maxApparentTemp: Double?,
      minApparentTemp: Double?,
      maxWindSpeed: Double?,
      minTempUnit: String?,
      maxTempUnit: String?,
      minApparentTempUnit: String?,
      maxApparentTempUnit: String?,
      maxWindSpeedUnit: String?,
      date: LocalDate,
    ): DailyWeather {

      val weatherCondition =
        when (val dataWeatherCondition = WeatherCondition.fromCode(weatherCode)) {
          is WeatherCondition.Cloudy -> com.itstanany.domain.weather.models.WeatherCondition.Cloudy(
            dataWeatherCondition.description
          )

          is WeatherCondition.Rainy -> {
            com.itstanany.domain.weather.models.WeatherCondition.Rainy(
              dataWeatherCondition.description
            )
          }

          is WeatherCondition.Sunny -> {
            com.itstanany.domain.weather.models.WeatherCondition.Sunny(
              dataWeatherCondition.description
            )
          }
        }
      return DailyWeather(
        maxTemp = maxTemp,
        maxTempUnit = maxTempUnit ?: "Unit Unavailable",
        minTemp = minTemp,
        minTempUnit = minTempUnit ?: "Unit Unavailable",
        maxApparentTemp = maxApparentTemp,
        maxApparentTempUnit = maxApparentTempUnit ?: "Unit Unavailable",
        minApparentTemp = minApparentTemp,
        minApparentTempUnit = minApparentTempUnit ?: "Unit Unavailable",
        condition = weatherCondition,
        maxWindSpeed = maxWindSpeed,
        maxWindSpeedUnit = maxWindSpeedUnit ?: "Unit Unavailable",
        date = date,
      )
    }
  }
}
