package com.itstanany.data.city.remote

import com.itstanany.data.city.models.CityResultDto
import javax.inject.Inject

class CityRemoteDataSourceImpl @Inject constructor(
  private val cityRemoteApiService: CityRemoteApiService,
) : CityRemoteDataSource {
  override suspend fun searchCities(query: String): List<CityResultDto> {
    return cityRemoteApiService.searchCities(query).results
  }
}
