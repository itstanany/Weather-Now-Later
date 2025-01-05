package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.WeatherForecastResponse

/**
 * Interface for a remote data source that provides weather forecast data.
 */
interface WeatherRemoteDataSource {
  /**
   * Retrieves the weather forecast for the specified location.
   *
   * @param latitude The latitude of the location.
   * @param longitude The longitude of the location.
   * @return A [WeatherForecastResponse] containing the forecast data.
   */
  suspend fun getForecast(
    latitude: Float,
    longitude: Float,
  ): WeatherForecastResponse
}
