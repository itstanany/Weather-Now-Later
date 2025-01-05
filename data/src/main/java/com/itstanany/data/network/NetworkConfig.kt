package com.itstanany.data.network

/**
 * Provides configuration values for network-related operations.
 *
 * This interface defines constants for base URLs used in API requests.
 * Implementations of this interface should provide the actual values for these constants.
 */
interface NetworkConfig {
  /**
   * The base URL for the weather forecast API.
   */
  val forecastApiBaseUrl: String
  /**
   * The base URL for the city API.
   */
  val cityApiBaseUrl: String
}
