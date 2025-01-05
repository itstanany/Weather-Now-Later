package com.itstanany.data.city.remote

import com.itstanany.data.city.models.CityResultDto
import javax.inject.Inject

/**
 * Implementation of the [CityRemoteDataSource] interface.
 *
 * This class is responsible for fetching city-related data from a remote server
 * using the provided [CityRemoteApiService].
 *
 * @property cityRemoteApiService The API service used to perform network requests for city data.
 */
class CityRemoteDataSourceImpl @Inject constructor(
  private val cityRemoteApiService: CityRemoteApiService,
) : CityRemoteDataSource {
  /**
   * Searches for cities based on the provided query string.
   *
   * This method overrides the [CityRemoteDataSource.searchCities] function and uses
   * the [cityRemoteApiService] to perform a network request to fetch city data.
   *
   * @param query The search string used to find matching cities.
   * @return A list of [CityResultDto] objects representing the search results.
   *         Returns `null` if no results are found or if an error occurs during the request.
   */
  override suspend fun searchCities(query: String): List<CityResultDto>? {
    return cityRemoteApiService.searchCities(query).results
  }
}
