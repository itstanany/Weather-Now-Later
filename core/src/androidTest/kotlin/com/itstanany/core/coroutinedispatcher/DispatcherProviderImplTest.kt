package com.itstanany.core.coroutinedispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DispatcherProviderImplTest {
  private lateinit var dispatcherProvider: DispatcherProviderImpl

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Before
  fun setup() {
    dispatcherProvider = DispatcherProviderImpl()
  }

  @Test
  fun getIODispatcher_shouldReturnIODispatcher() {
    // When
    val dispatcher = dispatcherProvider.io()

    // Then
    assertEquals(Dispatchers.IO, dispatcher)
  }

  @Test
  fun getMainDispatcher_shouldReturnMainDispatcher() {
    // When
    val dispatcher = dispatcherProvider.main()

    // Then
    assertEquals(Dispatchers.Main, dispatcher)
  }

  @Test
  fun getDefaultDispatcher_shouldReturnDefaultDispatcher() {
    // When
    val dispatcher = dispatcherProvider.default()

    // Then
    assertEquals(Dispatchers.Default, dispatcher)
  }

  @Test
  fun getAllDispatchers_shouldReturnDifferentInstances() {
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

class MainDispatcherRule : TestWatcher() {
  private val testDispatcher = StandardTestDispatcher()

  @OptIn(ExperimentalCoroutinesApi::class)
  override fun starting(description: Description) {
    Dispatchers.setMain(testDispatcher)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  override fun finished(description: Description) {
    Dispatchers.resetMain()
  }
}
