package com.itstanany.domain.network.repositories

import com.itstanany.domain.common.Result

interface NetworkRepository {
  fun isNetworkAvailable(): Result<Boolean>
}
