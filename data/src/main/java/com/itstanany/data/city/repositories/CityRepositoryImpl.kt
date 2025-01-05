package com.itstanany.data.city.repositories

import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.data.city.remote.CityRemoteDataSource
import com.itstanany.data.mapper.CityMapper
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An implementation of the [CityRepository] that manages city data from local and remote sources.
 *
 * This class uses the [CityLocalDataSource] for accessing and storing the last searched city,
 * and the [CityRemoteDataSource] for searching for cities. It also utilizes the [CityMapper]
 * to map data between data transfer objects (DTOs) and domain models.
 *
 * @param cityLocalDataSource The local data source for city data.
 * @param cityRemoteDataSource The remote data source for city data.
 * @param cityMapper The mapper used to convert data to domain models.
 * @constructor Creates an instance of [CityRepositoryImpl].
 */
class CityRepositoryImpl @Inject constructor(
  private val cityLocalDataSource: CityLocalDataSource,
  private val cityRemoteDataSource: CityRemoteDataSource,
  private val cityMapper: CityMapper,
) : CityRepository {

  /**
   * Retrieves a list of cities that match the given search term from the remote data source.
   *
   * @param cityName The search term to filter cities by.
   * @return A list of [City] objects matching the search term.
   */
  override suspend fun getAllCities(
    cityName: String,
  ): List<City> {
    val result = cityRemoteDataSource.searchCities(cityName)
    val cities = result?.mapNotNull {
      it.takeIf { it.latitude != null && it.longitude != null }?.let { validCityDto ->
        cityMapper.mapToDomain(validCityDto)
      }
    }
    return cities ?: listOf()
  }

  /**
   * Gets the last searched city as a Flow from the local data source.
   *
   * @return A Flow that emits the last searched [City] object or null if none exists.
   */
  override fun getLastSearchedCity(): Flow<City?> {
    return cityLocalDataSource.getLastSearchedCity()
  }

  /**
   * Saves the given city as the last searched city in the local data source.
   *
   * @param city The [City] object to save.
   */
  override suspend fun saveLastSearchedCity(city: City) {
    return cityLocalDataSource.saveCity(city)
  }
}