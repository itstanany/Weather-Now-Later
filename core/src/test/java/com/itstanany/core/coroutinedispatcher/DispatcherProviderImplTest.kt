package com.itstanany.core.coroutinedispatcher

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DispatcherProviderImplTest {
  private lateinit var dispatcherProvider: DispatcherProviderImpl

  @Before
  fun setup() {
    dispatcherProvider = DispatcherProviderImpl()
  }

  @Test
  fun `io returns IO dispatcher`() {
    // When
    val dispatcher = dispatcherProvider.io()

    // Then
    assertEquals(Dispatchers.IO, dispatcher)
  }

  @Test
  fun `main returns Main dispatcher`() {
    // When
    val dispatcher = dispatcherProvider.main()

    // Then
    assertEquals(Dispatchers.Main, dispatcher)
  }

  @Test
  fun `default returns Default dispatcher`() {
    // When
    val dispatcher = dispatcherProvider.default()

    // Then
    assertEquals(Dispatchers.Default, dispatcher)
  }

  @Test
  fun `dispatchers are not the same`() {
    // When
    val ioDispatcher = dispatcherProvider.io()
    val mainDispatcher = dispatcherProvider.main()
    val defaultDispatcher = dispatcherProvider.default()

    // Then
    assertNotEquals(ioDispatcher, mainDispatcher)
    assertNotEquals(ioDispatcher, defaultDispatcher)
    assertNotEquals(mainDispatcher, defaultDispatcher)
  }
}

