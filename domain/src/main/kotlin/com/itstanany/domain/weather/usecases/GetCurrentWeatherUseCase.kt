package com.itstanany.domain.weather.usecases

import com.itstanany.domain.base.BaseUseCaseWithParams
import com.itstanany.domain.city.models.City
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.common.Utils.safeCall
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.repositories.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository,
  dispatcherProvider: DispatcherProvider,
) : BaseUseCaseWithParams<City, DailyWeather>(dispatcherProvider.io()) {
  override suspend fun execute(params: City): Result<DailyWeather> {
    return safeCall {
      weatherRepository.getCurrentWeather(params)
    }
  }

}