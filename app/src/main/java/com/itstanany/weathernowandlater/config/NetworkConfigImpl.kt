package com.itstanany.weathernowandlater.config

import com.itstanany.data.network.NetworkConfig
import com.itstanany.weathernowandlater.BuildConfig.FORECAST_API_URL
import com.itstanany.weathernowandlater.BuildConfig.GEOCODING_API_URL
import javax.inject.Inject

class NetworkConfigImpl @Inject constructor() : NetworkConfig {
  override val forecastApiBaseUrl: String = FORECAST_API_URL
  override val cityApiBaseUrl: String = GEOCODING_API_URL
}
