package com.itstanany.domain.base

import com.itstanany.domain.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Base class for use cases that do not require any input parameters.
 *
 * This abstract class provides a common structure for use cases that perform
 * operations without requiring any input parameters. It handles the execution
 * of the use case on a specified dispatcher and wraps the result in a [Result]
 * object.
 *
 * @param dispatcher The dispatcher on which the use case should be executed.
 * @param ResultType The type of the result returned by the use case.
 */
abstract class BaseUseCaseNoParams<out ResultType>(
  private val dispatcher: CoroutineDispatcher
) {
  /**
   * Executes the use case on the specified dispatcher and returns the result.
   *
   * This function is an operator overload for the `invoke` operator, allowing
   * the use case to be called like a function. It wraps the execution of the
   * `execute()` function in a `withContext` block to ensure that it runs on
   * the correct dispatcher.
   *
   * @return A [Result] object representing the outcome of the use case execution.
   */
  suspend operator fun invoke(): Result<ResultType> =
    withContext(dispatcher) {
      execute()
    }

  /**
   * Abstract function that defines the core logic of the use case.
   *
   * Subclasses must implement this function to perform the actual operation
   * of the use case. It should return a [Result] object representing the
   * outcome of the operation.
   *
   * @return A [Result] object representing the outcome of the use case operation.
   */
  protected abstract suspend fun execute(): Result<ResultType>
}
