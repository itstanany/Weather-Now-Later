package com.itstanany.domain.base

import com.itstanany.domain.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


/**
 * Base class for use cases that require input parameters.
 *
 * This abstract class provides a common structure for use cases that perform
 * operations with input parameters. It handles the execution of the use case
 * on a specified dispatcher and wraps the result in a [Result] object.
 *
 * @param Params The type of the input parameters for the use case.
 * @param ResultType The type of the result returned by the use case.
 * @param dispatcher The dispatcher on which the use case should be executed.
 */
abstract class BaseUseCaseWithParams<in Params, out ResultType>(
  private val dispatcher: CoroutineDispatcher,
) {
  /**
   * Executes the use case on the specified dispatcher with the given parameters and returns the result.
   *
   * This function is an operator overload for the `invoke` operator, allowing
   * the use case to be called like a function. It wraps the execution of the
   * `execute()` function in a `withContext` block to ensure that it runs on
   * the correct dispatcher.
   *
   * @param params The input parameters for the use case.
   * @return A [Result] object representing the outcome of the use case execution.
   */
  suspend operator fun invoke(params: Params): Result<ResultType> = withContext(dispatcher) {
    execute(params)
  }

  /**
   * Abstract function that defines the core logic of the use case.
   *
   * Subclasses must implement this function to perform the actual operation
   * of the use case. It should return a [Result] object representing the
   * outcome of the operation.
   *
   * @param params The input parameters for the use case.
   * @return A [Result] object representing the outcome of the use case operation.
   */
  protected abstract suspend fun execute(params: Params): Result<ResultType>
}
