package com.itstanany.data.mapper

import com.itstanany.data.city.models.CityResultDto
import com.itstanany.domain.city.models.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityMapper @Inject constructor() {
  fun mapToDomain(dto: CityResultDto): City? {
    // Return null if required fields are missing
    if (dto.latitude == null || dto.longitude == null ||
      dto.name.isNullOrEmpty() || dto.country.isNullOrEmpty()) {
      return null
    }

    return City(
      name = dto.name,
      country = dto.country,
      longitude = dto.longitude.toFloat(),
      latitude = dto.latitude.toFloat(),
      id = dto.id ?: 0
    )
  }
}

