package com.itstanany.domain.common

import com.itstanany.domain.common.Result.*


object Utils {
  inline fun <T> safeCall(block: () -> T): Result<T> = try {
    Success(block())
  } catch (e: Exception) {
    Failure(e)
  }
}
