package com.itstanany.data.mapper

import com.itstanany.data.city.models.CityResultDto
import com.itstanany.domain.city.models.City
import javax.inject.Inject

class CityMapper @Inject constructor() {
  companion object {
    fun mapToDomain(dto: CityResultDto): City {
      return City(
        name = dto.name.orEmpty(),
        country = dto.country.orEmpty(),
        longitude = dto.longitude?.toFloat() ?: 0f,
        latitude = dto.latitude?.toFloat() ?: 0f,
        id = dto.id ?: 0
      )
    }
  }
}
