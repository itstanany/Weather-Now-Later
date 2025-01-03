package com.itstanany.data.weather.repositories

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(): WeatherRepository {
  override suspend fun getCurrentWeather(city: City): DailyWeather {
    TODO("Not yet implemented")
  }

  override suspend fun getForecast(city: City): List<DailyWeather> {
    TODO("Not yet implemented")
  }
}