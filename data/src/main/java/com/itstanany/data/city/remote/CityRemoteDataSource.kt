package com.itstanany.data.city.remote

import com.itstanany.data.city.models.CityResultDto

/**
 * A remote data source interface for fetching city-related data from a remote server.
 *
 * This interface defines a method to search for cities based on a query string.
 * The data is returned as a list of [CityResultDto] objects, which represent the search results.
 *
 * @see CityResultDto for the structure of the city result data.
 */
interface CityRemoteDataSource {
  /**
   * Searches for cities based on the provided query string.
   *
   * This is a suspending function that should be called from a coroutine or another suspending function.
   * It performs a network request to fetch city data that matches the query.
   *
   * @param query The search string used to find matching cities.
   * @return A list of [CityResultDto] objects representing the search results.
   *         Returns `null` if no results are found or if an error occurs during the request.
   */
  suspend fun searchCities(query: String): List<CityResultDto>?
}
