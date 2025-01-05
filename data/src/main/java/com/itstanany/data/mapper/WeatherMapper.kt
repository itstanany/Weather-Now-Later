package com.itstanany.data.mapper

import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A mapper class responsible for converting weather data to [DailyWeather] domain models.
 *
 * This class provides a single instance (`@Singleton`) and is used to map weather data
 * received from the API to domain models used within the application. It handles
 * mapping weather codes to [WeatherCondition] and provides default units for temperature
 * and wind speed if they are missing.
 *
 * @constructor Creates an instance of [WeatherMapper].
 */
@Singleton
class WeatherMapper @Inject constructor() {

  /**
   * Maps weather data to a [DailyWeather] domain model.
   *
   * This function performs the mapping, including converting weather codes to
   * [WeatherCondition] and providing default units for temperature and wind speed
   * if they are missing.
   *
   * @param weatherCode The weather code.
   * @param maxTemp The maximum temperature.
   * @param minTemp The minimum temperature.
   * @param maxApparentTemp The maximum apparent temperature.
   * @param minApparentTemp The minimum apparent temperature.
   * @param maxWindSpeed The maximum wind speed.
   * @param minTempUnit The unit for the minimum temperature.
   * @param maxTempUnit The unit for the maximum temperature.
   * @param minApparentTempUnit The unit for the minimum apparent temperature.
   * @param maxApparentTempUnit The unit for the maximum apparent temperature.
   * @param maxWindSpeedUnit The unit for the maximum wind speed.
   * @param date The date of the weather forecast.
   * @return A [DailyWeather] domain model.
   */
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

  /**
   * Maps a weather code to a [WeatherCondition].
   *
   * @param weatherCode The weather code.
   * @return A [WeatherCondition] representing the weather condition.
   */
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

  /**
   * Provides a default unit if the given unit is null.
   *
   * @return The given unit if it is not null, otherwise "Unit Unavailable".
   */
  private fun String?.orDefaultUnit(): String = this ?: "Unit Unavailable"
}

