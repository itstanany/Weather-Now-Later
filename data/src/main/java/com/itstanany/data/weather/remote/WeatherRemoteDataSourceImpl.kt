package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.WeatherForecastResponse
import javax.inject.Inject

/**
 * An implementation of the [WeatherRemoteDataSource] that retrieves weather data from a remote API.
 *
 * This class uses the [WeatherRemoteApiService] to fetch weather forecast data for a given location.
 *
 * @param weatherRemoteApiService The API service for retrieving weather data.
 * @constructor Creates an instance of [WeatherRemoteDataSourceImpl].
 */
class WeatherRemoteDataSourceImpl @Inject constructor(
  private val weatherRemoteApiService: WeatherRemoteApiService,
) : WeatherRemoteDataSource {
  /**
   * Retrieves the weather forecast for the specified location.
   *
   * @param latitude The latitude of the location.
   * @param longitude The longitude of the location.
   * @return A [WeatherForecastResponse] object containing the weather forecast data.
   */
  override suspend fun getForecast(latitude: Float, longitude: Float): WeatherForecastResponse {
    return weatherRemoteApiService.getForecast(latitude, longitude)
  }
}
