package com.itstanany.data.city.local

import com.itstanany.domain.city.models.City
import kotlinx.coroutines.flow.Flow

interface CityLocalDataSource {
  fun getLastSearchedCity(): Flow<City?>

  suspend fun saveCity(city: City)
}
