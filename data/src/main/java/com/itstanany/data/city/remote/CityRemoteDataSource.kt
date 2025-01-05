package com.itstanany.data.city.remote

import com.itstanany.data.city.models.CityResultDto

interface CityRemoteDataSource {
  suspend fun searchCities(query: String): List<CityResultDto>?
}
