package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteApiService {
  @GET("forecast")
  suspend fun getForecast(
    @Query("latitude") latitude: Float,
    @Query("longitude") longitude: Float,
    @Query("daily") daily: String = DEFAULT_DAILY_PARAMS,
    @Query("timezone") timezone: String = DEFAULT_TIMEZONE,
  ): WeatherForecastResponse

  companion object {
    private const val DEFAULT_DAILY_PARAMS =
      "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,wind_speed_10m_max,wind_direction_10m_dominant"
    private const val DEFAULT_TIMEZONE = "Africa/Cairo"
  }

}
