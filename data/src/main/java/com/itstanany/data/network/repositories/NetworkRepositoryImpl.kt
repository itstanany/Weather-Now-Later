package com.itstanany.data.network.repositories

import android.content.Context
import com.itstanany.core.network.NetworkUtils
import com.itstanany.domain.common.Utils.safeCall
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.repositories.NetworkRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * An implementation of the [NetworkRepository] that uses [NetworkUtils] to check network availability.
 *
 * This class provides a concrete implementation for checking the network connection status
 * by utilizing the `isNetworkAvailable()` function from the `NetworkUtils` class.
 *
 * @param context The application context.
 * @constructor Creates an instance of [NetworkRepositoryImpl].
 */
class NetworkRepositoryImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : NetworkRepository {
  /**
   * Checks if the device has an active network connection using [NetworkUtils].
   *
   * @return A [Result] object indicating whether the device has an active network connection.
   *         [Result.Success] with `true` if the device is connected to a network,
   *         [Result.Failure] with an exception if there is an error or the device is not connected.
   */
  override fun isNetworkAvailable(): Result<Boolean> = safeCall {
    NetworkUtils.isNetworkAvailable(context)
  }
}
