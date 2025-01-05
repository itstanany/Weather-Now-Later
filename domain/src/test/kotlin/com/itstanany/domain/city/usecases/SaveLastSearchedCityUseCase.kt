package com.itstanany.domain.city.usecases

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SaveLastSearchedCityUseCaseTest {
  @MockK
  private lateinit var cityRepository: CityRepository

  private lateinit var saveLastSearchedCityUseCase: SaveLastSearchedCityUseCase

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
    saveLastSearchedCityUseCase = SaveLastSearchedCityUseCase(
      cityRepository,
      dispatcherProvider
    )
  }

  @Test
  fun `invoke when repository saves successfully then returns success`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    coEvery { cityRepository.saveLastSearchedCity(city) } just Runs

    // When
    val result = saveLastSearchedCityUseCase(city)

    // Then
    assertTrue(result is Result.Success)
    coVerify(exactly = 1) { cityRepository.saveLastSearchedCity(city) }
  }

  @Test
  fun `invoke when repository throws exception then returns failure`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val exception = IOException("Database error")
    coEvery { cityRepository.saveLastSearchedCity(city) } throws exception

    // When
    val result = saveLastSearchedCityUseCase(city)

    // Then
    assertTrue(result is Result.Failure)
    assertEquals(exception, (result as Result.Failure).error)
    coVerify(exactly = 1) { cityRepository.saveLastSearchedCity(city) }
  }
}
