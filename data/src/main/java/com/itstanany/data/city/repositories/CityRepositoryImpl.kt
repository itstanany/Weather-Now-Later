package com.itstanany.data.city.repositories

import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.data.city.remote.CityRemoteDataSource
import com.itstanany.data.mapper.CityMapper
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A repository responsible for managing city data.
 *
 * This interface defines methods for retrieving a list of cities based on a search term,
 * getting the last searched city, and saving the last searched city.
 */
class CityRepositoryImpl @Inject constructor(
  private val cityLocalDataSource: CityLocalDataSource,
  private val cityRemoteDataSource: CityRemoteDataSource,
  private val cityMapper: CityMapper,
) : CityRepository {

  /**
   * Retrieves a list of cities that match the given search term.
   *
   * @param cityName The search term to filter cities by.
   * @return A list of [City] objects matching the search term.
   */
  override suspend fun getAllCities(
    cityName: String,
  ): List<City> {
    val result = cityRemoteDataSource.searchCities(cityName)
    val cities = result.mapNotNull {
      it.takeIf { it.latitude != null && it.longitude != null }?.let { validCityDto ->
        cityMapper.mapToDomain(validCityDto)
      }
    }
    return cities
  }

  /**
   * Gets the last searched city as a Flow.
   *
   * @return A Flow that emits the last searched [City] object or null if none exists.
   */
  override fun getLastSearchedCity(): Flow<City?> {
    return cityLocalDataSource.getLastSearchedCity()
  }

  /**
   * Saves the given city as the last searched city.
   *
   * @param city The [City] object to save.
   */
  override suspend fun saveLastSearchedCity(city: City) {
    return cityLocalDataSource.saveCity(city)
  }
}