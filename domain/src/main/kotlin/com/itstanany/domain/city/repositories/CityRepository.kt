package com.itstanany.domain.city.repositories

import com.itstanany.domain.city.models.City
import kotlinx.coroutines.flow.Flow

/**
 * A repository responsible for managing city data.
 *
 * This interface defines methods for retrieving a list of cities based on a search term,
 * getting the last searched city, and saving the last searched city.
 */
interface CityRepository {
  /**
   * Retrieves a list of cities that match the given search term.
   *
   * @param cityName The search term to filter cities by.
   * @return A list of [City] objects matching the search term.
   */
  suspend fun getAllCities(
    cityName: String,
  ): List<City>

  /**
   * Gets the last searched city as a Flow.
   *
   * @return A Flow that emits the last searched [City] object or null if none exists.
   */
  fun getLastSearchedCity(): Flow<City?>
  /**
   * Saves the given city as the last searched city.
   *
   * @param city The [City] object to save.
   */
  suspend fun saveLastSearchedCity(city: City)
}
