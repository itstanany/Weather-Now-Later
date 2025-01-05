package com.itstanany.domain.city.usecases

import com.itstanany.domain.base.BaseUseCaseWithParams
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.common.Utils.safeCall
import javax.inject.Inject


/**
 * A use case responsible for saving the last searched city.
 *
 * This use case interacts with the [CityRepository] to save the given city as the
 * last searched city. It executes on the IO dispatcher provided by the
 * [DispatcherProvider].
 *
 * @param cityRepository The repository used to access city data.
 * @param dispatcher Provides dispatchers for coroutine execution.
 * @constructor Creates an instance of [SaveLastSearchedCityUseCase].
 */
class SaveLastSearchedCityUseCase @Inject constructor(
  private val cityRepository: CityRepository,
  dispatcher: DispatcherProvider,
  ): BaseUseCaseWithParams<City, Unit>(dispatcher.io()) {

  /**
   * Executes the use case to save the given city as the last searched city.
   *
   * @param params The [City] object to save as the last searched city.
   * @return A [Result] indicating success or failure of the save operation.
   */
    override suspend fun execute(params: City): Result<Unit> {
    return safeCall {
      cityRepository.saveLastSearchedCity(params)
    }
  }
}
