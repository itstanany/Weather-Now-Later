package com.itstanany.data.city.repositories

import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
  private val cityLocalDataSource: CityLocalDataSource,
): CityRepository {
  override suspend fun getAllCities(): List<City> {
    TODO("Not yet implemented")
  }

  override fun getLastSearchedCity(): Flow<City?> {
    return cityLocalDataSource.getLastSearchedCity()
  }

  override suspend fun saveLastSearchedCity(city: City) {
    TODO("Not yet implemented")
  }
}