package com.itstanany.data.city.remote

import com.itstanany.data.city.models.CityResultDto
import com.itstanany.domain.city.models.City

interface CityRemoteDataSource {
  suspend fun searchCities(query: String): List<CityResultDto>
}
