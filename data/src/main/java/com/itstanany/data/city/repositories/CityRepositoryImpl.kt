package com.itstanany.data.city.repositories

import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.data.city.remote.CityRemoteDataSource
import com.itstanany.data.mapper.CityMapper
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
  private val cityLocalDataSource: CityLocalDataSource,
  private val cityRemoteDataSource: CityRemoteDataSource,
) : CityRepository {
  override suspend fun getAllCities(
    cityName: String,
  ): List<City> {
    val result = cityRemoteDataSource.searchCities(cityName)
    val cities = result.mapNotNull {
      it.takeIf { it.latitude != null && it.longitude != null }?.let { validCityDto ->
        CityMapper.mapToDomain(validCityDto)
      }
    }
    return cities
  }

  override fun getLastSearchedCity(): Flow<City?> {
    return cityLocalDataSource.getLastSearchedCity()
  }

  override suspend fun saveLastSearchedCity(city: City) {
    TODO("Not yet implemented")
  }
}