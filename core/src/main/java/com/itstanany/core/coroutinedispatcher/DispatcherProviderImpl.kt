package com.itstanany.core.coroutinedispatcher

import com.itstanany.domain.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Implementation of the [DispatcherProvider] interface that provides [CoroutineDispatcher] instances
 * for different threading contexts.
 *
 * This class is used to abstract the creation of coroutine dispatchers, making it easier to
 * inject and test coroutine contexts in an application.
 *
 * @see DispatcherProvider for the interface definition.
 */
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

  /**
   * Provides a [CoroutineDispatcher] optimized for IO-bound operations.
   *
   * @return The [Dispatchers.IO] coroutine dispatcher.
   */
  override fun io(): CoroutineDispatcher = Dispatchers.IO

  /**
   * Provides a [CoroutineDispatcher] optimized for main-thread operations, typically used for UI updates.
   *
   * @return The [Dispatchers.Main] coroutine dispatcher.
   */
  override fun main(): CoroutineDispatcher = Dispatchers.Main

  /**
   * Provides a [CoroutineDispatcher] optimized for CPU-intensive operations.
   *
   * @return The [Dispatchers.Default] coroutine dispatcher.
   */
  override fun default(): CoroutineDispatcher = Dispatchers.Default
}
