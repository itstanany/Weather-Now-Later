package com.itstanany.domain.network.usecases

import com.itstanany.domain.base.BaseUseCaseNoParams
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.repositories.NetworkRepository
import javax.inject.Inject

/**
 * A use case responsible for checking the network availability.
 *
 * This use case interacts with the [NetworkRepository] to determine if the device
 * has an active network connection. It executes on the IO dispatcher provided by
 * the [DispatcherProvider].
 *
 * @param networkRepository The repository used to access network information.
 * @param dispatcher Provides dispatchers for coroutine execution.
 * @constructor Creates an instance of [CheckNetworkAvailabilityUseCase].
 */
class CheckNetworkAvailabilityUseCase @Inject constructor(
  private val networkRepository: NetworkRepository,
  dispatcher: DispatcherProvider,
) : BaseUseCaseNoParams<Boolean>(dispatcher.io()) {

  /**
   * Executes the use case to check the network availability.
   *
   * @return A [Result] object indicating whether the device has an active network connection.
   *         [Result.Success] with `true` if the device is connected to a network,
   *         [Result.Failure] with an exception if there is an error or the device is not connected.
   */
  override suspend fun execute(): Result<Boolean> {
    return networkRepository.isNetworkAvailable()
  }
}
