package com.itstanany.domain.city.usecases

import com.itstanany.domain.base.BaseUseCaseWithParams
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.common.Utils.safeCall
import javax.inject.Inject

/**
 * A use case responsible for searching for cities based on a given query.
 *
 * This use case interacts with the [CityRepository] to retrieve a list of cities
 * matching the query and returns the result wrapped in a [Result] object. It executes
 * on the IO dispatcher provided by the [DispatcherProvider].
 *
 * @param cityRepository The repository used to access city data.
 * @param dispatcher Provides dispatchers for coroutine execution.
 * @constructor Creates an instance of [SearchCitiesUseCase].
 */
class SearchCitiesUseCase @Inject constructor(
  private val cityRepository: CityRepository,
  dispatcher: DispatcherProvider,
) : BaseUseCaseWithParams<String, List<City>>(dispatcher.io()) {

  /**
   * Executes the use case to search for cities matching the given query.
   *
   * @param params The search query string.
   * @return A [Result] containing the list of [City] objects matching the query or an error.
   */
  override suspend fun execute(params: String): Result<List<City>> {
    return safeCall {
      cityRepository.getAllCities(params)
    }
  }
}
