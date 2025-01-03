package com.itstanany.domain.common

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
  fun io(): CoroutineDispatcher
  fun main(): CoroutineDispatcher
  fun default(): CoroutineDispatcher
}
