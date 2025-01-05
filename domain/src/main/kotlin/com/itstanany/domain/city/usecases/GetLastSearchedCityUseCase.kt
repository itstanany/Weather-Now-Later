package com.itstanany.domain.city.usecases

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLastSearchedCityUseCase @Inject constructor(
  private val cityRepository: CityRepository,
) {
  operator fun invoke(): Flow<City?> = cityRepository.getLastSearchedCity()
}
