package com.itstanany.domain.network.usecases

import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.repositories.NetworkRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException


class CheckNetworkAvailabilityUseCaseTest {
  @MockK
  private lateinit var networkRepository: NetworkRepository

  private lateinit var checkNetworkAvailabilityUseCase: CheckNetworkAvailabilityUseCase

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = StandardTestDispatcher(testScheduler)
  private val dispatcherProvider = object : DispatcherProvider {
    override fun io() = testDispatcher
    override fun main() = testDispatcher
    override fun default() = testDispatcher
  }

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    checkNetworkAvailabilityUseCase = CheckNetworkAvailabilityUseCase(
      networkRepository,
      dispatcherProvider
    )
  }

  @Test
  fun `invoke when network is available then returns success with true`() =
    runTest(testDispatcher) {
      // Given
      coEvery { networkRepository.isNetworkAvailable() } returns Result.Success(true)

      // When
      val result = checkNetworkAvailabilityUseCase()

      // Then
      assertTrue(result is Result.Success)
      assertTrue((result as Result.Success).data)
      coVerify(exactly = 1) { networkRepository.isNetworkAvailable() }
    }

  @Test
  fun `invoke when network is not available then returns success with false`() =
    runTest(testDispatcher) {
      // Given
      coEvery { networkRepository.isNetworkAvailable() } returns Result.Success(false)

      // When
      val result = checkNetworkAvailabilityUseCase()

      // Then
      assertTrue(result is Result.Success)
      assertFalse((result as Result.Success).data)
      coVerify(exactly = 1) { networkRepository.isNetworkAvailable() }
    }

  @Test
  fun `invoke when repository throws exception then returns failure`() = runTest(testDispatcher) {
    // Given
    val exception = IOException("Network error")
    coEvery { networkRepository.isNetworkAvailable() } returns Result.Failure(
      exception
    )

    // When
    val result = checkNetworkAvailabilityUseCase()

    // Then
    assertTrue(result is Result.Failure)
    assertEquals(exception, (result as Result.Failure).error)
    coVerify(exactly = 1) { networkRepository.isNetworkAvailable() }
  }
}

