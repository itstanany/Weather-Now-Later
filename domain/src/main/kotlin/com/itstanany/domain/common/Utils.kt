package com.itstanany.domain.common

import com.itstanany.domain.common.Result.Failure
import com.itstanany.domain.common.Result.Success

/**
 * Provides utility functions for common operations.
 *
 * This object contains helper functions that can be used across the application
 * to simplify common tasks, such as handling exceptions and performing safe calls.
 */
object Utils {

  /**
   * Executes a given block of code and wraps the result in a [Result] object.
   *
   * This function handles any exceptions thrown by the block and returns a [Result.Failure]
   * if an exception occurs. If the block executes successfully, it returns a [Result.Success]
   * with the result of the block.
   *
   * @param block The block of code to execute.
   * @return A [Result] object representing the outcome of the execution.
   */
  inline fun <T> safeCall(block: () -> T): Result<T> = try {
    Success(block())
  } catch (e: Exception) {
    Failure(e)
  }
}
