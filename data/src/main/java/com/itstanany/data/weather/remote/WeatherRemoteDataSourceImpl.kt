package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.WeatherForecastResponse
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
  private val weatherRemoteApiService: WeatherRemoteApiService,
) : WeatherRemoteDataSource {
  override suspend fun getForecast(latitude: Float, longitude: Float): WeatherForecastResponse {
    return weatherRemoteApiService.getForecast(latitude, longitude)
  }
}
