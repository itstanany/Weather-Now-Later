package com.itstanany.domain.base

import com.itstanany.domain.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCaseNoParams<out ResultType>(
  private val dispatcher: CoroutineDispatcher
) {
  suspend operator fun invoke(): Result<ResultType> =
    withContext(dispatcher) {
      execute()
    }

  protected abstract suspend fun execute(): Result<ResultType>
}
