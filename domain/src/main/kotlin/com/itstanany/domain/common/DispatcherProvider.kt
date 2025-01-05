package com.itstanany.domain.common

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provides coroutine dispatchers for different contexts.
 *
 * This interface defines methods for retrieving dispatchers for IO operations,
 * main thread operations, and default operations. Implementations of this interface
 * can be used to inject different dispatchers for testing or to customize the
 * execution behavior of coroutines.
 */
interface DispatcherProvider {
  /**
   * Returns the dispatcher for IO operations.
   *
   * This dispatcher should be used for tasks that involve blocking I/O operations,
   * such as network requests or disk access.
   *
   * @return The dispatcher for IO operations.
   */
  fun io(): CoroutineDispatcher
  /**
   * Returns the dispatcher for main thread operations.
   *
   * This dispatcher should be used for tasks that need to run on the main thread,
   * such as updating the UI.
   *
   * @return The dispatcher for main thread operations.
   */
  fun main(): CoroutineDispatcher
  /**
   * Returns the dispatcher for default operations.
   *
   * This dispatcher should be used for CPU-bound tasks that don't require a
   * specific dispatcher.
   *
   * @return The dispatcher for default operations.
   */
  fun default(): CoroutineDispatcher
}
