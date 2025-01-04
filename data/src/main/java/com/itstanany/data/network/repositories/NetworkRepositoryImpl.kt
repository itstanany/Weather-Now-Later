package com.itstanany.data.network.repositories

import android.content.Context
import com.itstanany.core.network.NetworkUtils
import com.itstanany.domain.common.Utils.safeCall
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.repositories.NetworkRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NetworkRepositoryImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : NetworkRepository {
  override fun isNetworkAvailable(): Result<Boolean> = safeCall {
    NetworkUtils.isNetworkAvailable(context)
  }
}
