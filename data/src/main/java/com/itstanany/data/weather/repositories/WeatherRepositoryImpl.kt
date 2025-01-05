package com.itstanany.data.weather.repositories

import com.itstanany.data.mapper.WeatherMapper
import com.itstanany.data.weather.remote.WeatherRemoteDataSource
import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.repositories.WeatherRepository
import com.itstanany.weathernowandlater.weather_utils.DateUtils
import javax.inject.Inject

/**
 * An implementation of the [WeatherRepository] that retrieves weather data from a remote data source.
 *
 * This class uses the [WeatherRemoteDataSource] to fetch weather data and the [WeatherMapper]
 * to map the data to domain models.
 *
 * @param weatherRemoteDataSource The remote data source for weather data.
 * @param weatherMapper The mapper used to convert data to domain models.
 * @constructor Creates an instance of [WeatherRepositoryImpl].
 */
class WeatherRepositoryImpl @Inject constructor(
  private val weatherRemoteDataSource: WeatherRemoteDataSource,
  private val weatherMapper: WeatherMapper,
) : WeatherRepository {


  /**
   * Retrieves the current weather for the specified city from the remote data source.
   *
   * @param city The [City] for which to retrieve the current weather.
   * @return A [DailyWeather] object representing the current weather conditions.
   * @throws Exception If the response from the remote data source is invalid.
   */
  override suspend fun getCurrentWeather(city: City): DailyWeather {
    val response = weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    if (response.daily?.weatherCode?.firstOrNull() == null
      || response.daily.temperature2mMax?.firstOrNull() == null
      || response.daily.temperature2mMin?.firstOrNull() == null
      || response.dailyUnits == null
      || response.daily.time?.firstOrNull() == null
    ) {
      throw Exception("Invalid response")
    }
    val dailyWeather = weatherMapper.mapToDomain(
      weatherCode = response.daily.weatherCode.first()!!,
      maxTemp = response.daily.temperature2mMax.first()!!,
      minTemp = response.daily.temperature2mMin.first()!!,
      maxApparentTemp = response.daily.apparentTemperatureMax?.first(),
      minApparentTemp = response.daily.apparentTemperatureMin?.first(),
      maxWindSpeed = response.daily.windSpeed10mMax?.first(),
      minTempUnit = response.dailyUnits.temperature2mMin,
      maxTempUnit = response.dailyUnits.temperature2mMax,
      minApparentTempUnit = response.dailyUnits.apparentTemperatureMin,
      maxApparentTempUnit = response.dailyUnits.apparentTemperatureMax,
      maxWindSpeedUnit = response.dailyUnits.windSpeed10mMax,
      date = DateUtils.parseDate(response.daily.time.first()!!),
    )
    return dailyWeather
  }

  /**
   * Retrieves the weather forecast for the specified city from the remote data source.
   *
   * @param city The [City] for which to retrieve the forecast.
   * @return A list of [DailyWeather] objects representing the forecast for the coming days.
   * @throws Exception If the response from the remote data source is invalid.
   */
  override suspend fun getForecast(city: City): List<DailyWeather> {

    val response = weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    if (response.daily?.weatherCode?.firstOrNull() == null
      || response.daily.temperature2mMax?.firstOrNull() == null
      || response.daily.temperature2mMin?.firstOrNull() == null
      || response.dailyUnits == null
    ) {
      throw Exception("Invalid response")
    }
    val dailyWeatherForecast = response.daily.weatherCode.mapIndexed { index, weatherCode ->
      weatherMapper.mapToDomain(
        weatherCode = weatherCode!!,
        maxTemp = response.daily.temperature2mMax[index]!!,
        minTemp = response.daily.temperature2mMin[index]!!,
        maxApparentTemp = response.daily.apparentTemperatureMax?.get(index),
        minApparentTemp = response.daily.apparentTemperatureMin?.get(index),
        maxWindSpeed = response.daily.windSpeed10mMax?.get(index),
        minTempUnit = response.dailyUnits.temperature2mMin,
        maxTempUnit = response.dailyUnits.temperature2mMax,
        minApparentTempUnit = response.dailyUnits.apparentTemperatureMin,
        maxApparentTempUnit = response.dailyUnits.apparentTemperatureMax,
        maxWindSpeedUnit = response.dailyUnits.windSpeed10mMax,
        date = DateUtils.parseDate(response.daily.time?.get(index)!!),
      )
    }
    return dailyWeatherForecast
  }
}