package com.itstanany.domain.common

/**
 * Represents the result of an operation that can either be a success with a value or a failure with an error.
 *
 * This sealed class is used to handle the different outcomes of asynchronous operations and provide a consistent way to represent success or failure.
 */
sealed class Result<out T> {
  /**
   * Represents a successful operation with a value.
   *
   * @param data The value returned by the successful operation.
   */
  data class Success<out T>(val data: T) : Result<T>()
  /**
   * Represents a failed operation with an error.
   *
   * @param error The error that occurred during the operation.
   */
  data class Failure(val error: Throwable) : Result<Nothing>()
}
