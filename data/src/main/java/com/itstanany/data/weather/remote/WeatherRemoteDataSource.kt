package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.WeatherForecastResponse

interface WeatherRemoteDataSource {
  suspend fun getForecast(
    latitude: Float,
    longitude: Float,
  ): WeatherForecastResponse
}
