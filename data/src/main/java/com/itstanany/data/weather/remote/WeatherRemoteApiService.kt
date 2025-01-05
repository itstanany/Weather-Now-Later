package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the API service for retrieving weather data from a remote server.
 *
 * This interface declares the endpoints for fetching weather forecast information.
 * It uses Retrofit annotations to define the HTTP methods and parameters for each request.
 */
interface WeatherRemoteApiService {
  /**
   * Retrieves the weather forecast for a given location.
   *
   * This function makes a GET request to the "forecast" endpoint with the specified
   * latitude and longitude. It also includes optional parameters for customizing
   * the daily data and timezone.
   *
   * @param latitude The latitude of the location.
   * @param longitude The longitude of the location.
   * @param daily A comma-separated list of daily parameters to include in the response.
   *              Defaults to [DEFAULT_DAILY_PARAMS].
   * @param timezone The timezone for the forecast. Defaults to [DEFAULT_TIMEZONE].
   * @return A [WeatherForecastResponse] object containing the weather forecast data.
   */
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
