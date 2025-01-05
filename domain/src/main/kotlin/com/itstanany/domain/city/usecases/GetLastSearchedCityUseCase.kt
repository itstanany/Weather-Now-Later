package com.itstanany.domain.city.usecases

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case responsible for retrieving the last searched city.
 *
 * This use case interacts with the [CityRepository] to retrieve the last searched
 * city and returns it as a [Flow].
 *
 * @param cityRepository The repository used to access city data.
 * @constructor Creates an instance of [GetLastSearchedCityUseCase].
 */
class GetLastSearchedCityUseCase @Inject constructor(
  private val cityRepository: CityRepository,
) {
  /**
   * Retrieves the last searched city as a [Flow].
   *
   * @return A [Flow] that emits the last searched [City] object or null if none exists.
   */
  operator fun invoke(): Flow<City?> = cityRepository.getLastSearchedCity()
}
