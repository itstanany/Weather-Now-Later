package com.itstanany.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utility object for network-related operations.
 *
 */
object NetworkUtils {
  /**
   * Checks if the device has an active internet connection.
   *
   * This function determines whether the device is currently connected to the internet
   * by checking for an active network with internet capabilities.
   *
   * @param context The context of the application.
   * @return `true` if the device has an active internet connection, `false` otherwise.
   */
  fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
  }
}
