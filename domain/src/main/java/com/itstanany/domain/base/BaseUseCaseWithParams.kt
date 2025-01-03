package com.itstanany.domain.base

import com.itstanany.domain.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


abstract class BaseUseCaseWithParams<in Params, out ResultType>(
  private val dispatcher: CoroutineDispatcher,
) {
  suspend operator fun invoke(params: Params): Result<ResultType> = withContext(dispatcher) {
    execute(params)
  }

  protected abstract suspend fun execute(params: Params): Result<ResultType>
}
