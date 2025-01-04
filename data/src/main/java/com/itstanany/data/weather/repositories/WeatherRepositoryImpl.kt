package com.itstanany.data.weather.repositories

import com.itstanany.data.mapper.WeatherMapper
import com.itstanany.data.weather.remote.WeatherRemoteDataSource
import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
  private val weatherRemoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {
  override suspend fun getCurrentWeather(city: City): DailyWeather {
    val response = weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    if (response.daily?.weatherCode?.firstOrNull() == null
      || response.daily.temperature2mMax?.firstOrNull() == null
      || response.daily.temperature2mMin?.firstOrNull() == null
      || response.dailyUnits == null
    ) {
      throw Exception("Invalid response")
    }
    val dailyWeather = WeatherMapper.mapToDomain(
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
    )
    return dailyWeather
  }

  override suspend fun getForecast(city: City): List<DailyWeather> {
    TODO("Not yet implemented")
  }
}