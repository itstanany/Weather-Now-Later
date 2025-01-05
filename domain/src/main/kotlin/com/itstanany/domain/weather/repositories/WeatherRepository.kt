package com.itstanany.domain.weather.repositories

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather

/**
 * A repository responsible for providing weather data.
 *
 * This interface defines methods for retrieving current weather and forecast data
 * for a given city. Implementations of this interface should handle the logic
 * for fetching weather data from a data source (e.g., a remote API or local database).
 */
interface WeatherRepository {
  /**
   * Retrieves the current weather for the specified city.
   *
   * @param city The [City] for which to retrieve the current weather.
   * @return A [DailyWeather] object representing the current weather conditions.
   */
  suspend fun getCurrentWeather(city: City): DailyWeather
  /**
   * Retrieves the weather forecast for the specified city.
   *
   * @param city The [City] for which to retrieve the forecast.
   * @return A list of [DailyWeather] objects representing the forecast for the coming days.
   */
  suspend fun getForecast(city: City): List<DailyWeather>
}
