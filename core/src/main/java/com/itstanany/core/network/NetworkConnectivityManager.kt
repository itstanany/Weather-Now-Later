package com.itstanany.core.network

interface NetworkConnectivityManager {
  suspend fun hasInternetConnection(): Boolean
}
