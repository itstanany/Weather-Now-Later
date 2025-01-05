package com.itstanany.domain.weather.usecases

import com.itstanany.domain.base.BaseUseCaseWithParams
import com.itstanany.domain.city.models.City
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.common.Utils.safeCall
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.repositories.WeatherRepository
import javax.inject.Inject

/**
 * A use case responsible for retrieving the weather forecast for a given city.
 *
 * This use case interacts with the [WeatherRepository] to fetch the forecast data
 * and returns the result wrapped in a [Result] object. It executes on the IO dispatcher
 * provided by the [DispatcherProvider].
 *
 * @param weatherRepository The repository used to access weather data.
 * @param dispatcherProvider Provides dispatchers for coroutine execution.
 * @constructor Creates an instance of [GetForecastUseCase].
 */
class GetForecastUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository,
  dispatcherProvider: DispatcherProvider,
) : BaseUseCaseWithParams<City, List<DailyWeather>>(dispatcherProvider.io()) {

  /**
   * Executes the use case to retrieve the forecast for the given city.
   *
   * @param params The [City] for which to retrieve the forecast.
   * @return A [Result] containing the list of [DailyWeather] objects or an error.
   */
  override suspend fun execute(params: City): Result<List<DailyWeather>> {
    return safeCall {
      return@safeCall weatherRepository.getForecast(params)
    }
  }
}
