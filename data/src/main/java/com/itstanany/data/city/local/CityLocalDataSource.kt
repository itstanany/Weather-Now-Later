package com.itstanany.data.city.local

import com.itstanany.domain.city.models.City
import kotlinx.coroutines.flow.Flow

/**
 * A data source responsible for managing local city data.
 *
 * This interface defines methods for accessing and storing city information locally.
 * Implementations of this interface should handle the persistence of city data,
 * such as saving the last searched city.
 */
interface CityLocalDataSource {
  /**
   * Retrieves the last searched city as a Flow.
   *
   * @return A Flow that emits the last searched [City] object or null if none exists.
   */
  fun getLastSearchedCity(): Flow<City?>

  /**
   * Saves the given city as the last searched city.
   *
   * @param city The [City] object to save.
   */
  suspend fun saveCity(city: City)
}
