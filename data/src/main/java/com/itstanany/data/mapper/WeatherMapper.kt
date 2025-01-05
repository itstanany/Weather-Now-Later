package com.itstanany.data.mapper

import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherMapper @Inject constructor() {
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
    return DailyWeather(
      maxTemp = maxTemp,
      maxTempUnit = maxTempUnit.orDefaultUnit(),
      minTemp = minTemp,
      minTempUnit = minTempUnit.orDefaultUnit(),
      maxApparentTemp = maxApparentTemp,
      maxApparentTempUnit = maxApparentTempUnit.orDefaultUnit(),
      minApparentTemp = minApparentTemp,
      minApparentTempUnit = minApparentTempUnit.orDefaultUnit(),
      condition = mapWeatherCondition(weatherCode),
      maxWindSpeed = maxWindSpeed,
      maxWindSpeedUnit = maxWindSpeedUnit.orDefaultUnit(),
      date = date,
    )
  }

  private fun mapWeatherCondition(weatherCode: Int): WeatherCondition {
    return when (val dataWeatherCondition =
      com.itstanany.data.weather.models.WeatherCondition.fromCode(weatherCode)) {
      is com.itstanany.data.weather.models.WeatherCondition.Cloudy -> WeatherCondition.Cloudy(
        dataWeatherCondition.description
      )

      is com.itstanany.data.weather.models.WeatherCondition.Rainy -> WeatherCondition.Rainy(
        dataWeatherCondition.description
      )

      is com.itstanany.data.weather.models.WeatherCondition.Sunny -> WeatherCondition.Sunny(
        dataWeatherCondition.description
      )
    }
  }

  private fun String?.orDefaultUnit(): String = this ?: "Unit Unavailable"
}

