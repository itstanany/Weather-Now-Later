package com.itstanany.domain.network.usecases

import com.itstanany.domain.base.BaseUseCaseNoParams
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.repositories.NetworkRepository
import javax.inject.Inject

class CheckNetworkAvailabilityUseCase @Inject constructor(
  private val networkRepository: NetworkRepository,
  dispatcher: DispatcherProvider,
) : BaseUseCaseNoParams<Boolean>(dispatcher.io()) {

  override suspend fun execute(): Result<Boolean> {
    return networkRepository.isNetworkAvailable()
  }
}
