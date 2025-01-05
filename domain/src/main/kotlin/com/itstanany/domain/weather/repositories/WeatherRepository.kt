package com.itstanany.domain.weather.repositories

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather

interface WeatherRepository {
  suspend fun getCurrentWeather(city: City): DailyWeather

  suspend fun getForecast(city: City): List<DailyWeather>
}
