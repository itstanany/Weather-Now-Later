package com.itstanany.data.mapper

import com.itstanany.data.city.models.CityResultDto
import com.itstanany.domain.city.models.City
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A mapper class responsible for converting objects to [City] domain models.
 *
 * This class provides a single instance (`@Singleton`) and is used to map data transfer objects
 * received from the API to domain models used within the application.
 *
 * @constructor Creates an instance of [CityMapper].
 */
@Singleton
class CityMapper @Inject constructor() {
  /**
   * Maps a [CityResultDto] object to a [City] domain model.
   *
   * This function performs the mapping and handles cases where required fields are missing
   * in the DTO. If any required field is missing, it returns `null`.
   *
   * @param dto The [CityResultDto] object to map.
   * @return A [City] domain model if all required fields are present in the DTO, otherwise `null`.
   */
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

