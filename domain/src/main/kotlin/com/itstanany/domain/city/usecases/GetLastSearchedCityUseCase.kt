package com.itstanany.domain.city.usecases

import com.itstanany.domain.city.repositories.CityRepository
import javax.inject.Inject


class GetLastSearchedCityUseCase @Inject constructor(
  private val cityRepository: CityRepository,
) {
  operator fun invoke() = cityRepository.getLastSearchedCity()
}
