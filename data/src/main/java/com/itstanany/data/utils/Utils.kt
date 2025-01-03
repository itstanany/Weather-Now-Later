package com.itstanany.data.utils

import com.itstanany.domain.common.Result
import com.itstanany.domain.common.Result.*


internal object Utils {
  inline fun <T> safeCall(block: () -> T): Result<T> = try {
    Success(block())
  } catch (e: Exception) {
    Failure(e)
  }
}
