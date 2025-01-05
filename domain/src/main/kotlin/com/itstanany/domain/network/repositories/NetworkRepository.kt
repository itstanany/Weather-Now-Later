package com.itstanany.domain.network.repositories

import com.itstanany.domain.common.Result

/**
 * A repository responsible for providing information about the network connection status.
 *
 * This interface defines a method for checking if the device has an active network connection.
 * Implementations of this interface should handle the logic for determining the network
 * connection status and returning the result as a [Result] object.
 */
interface NetworkRepository {
  /**
   * Checks if the device has an active network connection and connect to global network.
   *
   * @return A [Result] object indicating whether the device has an active network connection.
   *         [Result.Success] with `true` if the device is connected to a network,
   *         [Result.Failure] with an exception if there is an error or the device is not connected.
   */
  fun isNetworkAvailable(): Result<Boolean>
}
