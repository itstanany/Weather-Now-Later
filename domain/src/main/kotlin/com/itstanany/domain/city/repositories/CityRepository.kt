package com.itstanany.domain.city.repositories

import com.itstanany.domain.city.models.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
  suspend fun getAllCities(
    cityName: String,
  ): List<City>

  fun getLastSearchedCity(): Flow<City?>

  suspend fun saveLastSearchedCity(city: City)
}
