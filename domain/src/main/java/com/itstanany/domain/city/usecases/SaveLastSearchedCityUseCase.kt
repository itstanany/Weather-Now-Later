package com.itstanany.domain.city.usecases

import com.itstanany.domain.base.BaseUseCaseWithParams
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.common.Utils.safeCall
import javax.inject.Inject

class SaveLastSearchedCityUseCase @Inject constructor(
  private val cityRepository: CityRepository,
  dispatcher: DispatcherProvider,
  ): BaseUseCaseWithParams<City, Unit>(dispatcher.io()) {
  override suspend fun execute(params: City): Result<Unit> {
    return safeCall {
      cityRepository.saveLastSearchedCity(params)
    }
  }
}
