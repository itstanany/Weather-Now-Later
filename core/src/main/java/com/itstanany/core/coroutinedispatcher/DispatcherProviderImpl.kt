package com.itstanany.core.coroutinedispatcher

import com.itstanany.domain.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun main(): CoroutineDispatcher = Dispatchers.Main
  override fun default(): CoroutineDispatcher = Dispatchers.Default
}
