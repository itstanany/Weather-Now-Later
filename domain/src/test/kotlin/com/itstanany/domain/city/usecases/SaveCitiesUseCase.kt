package com.itstanany.domain.city.usecases

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCitiesUseCaseTest {
  @MockK
  private lateinit var cityRepository: CityRepository

  private lateinit var searchCitiesUseCase: SearchCitiesUseCase

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
    searchCitiesUseCase = SearchCitiesUseCase(
      cityRepository,
      dispatcherProvider
    )
  }

  @Test
  fun `execute when repository returns cities then returns success with cities`() =
    runTest(testDispatcher) {
      // Given
      val query = "London"
      val cities = listOf(
        City("London", "UK", 51.5074f, -0.1278f, 1),
        City("London", "CA", 42.9849f, -81.2453f, 2)
      )
      coEvery { cityRepository.getAllCities(query) } returns cities

      // When
      val result = searchCitiesUseCase(query)

      // Then
      assertTrue(result is Result.Success)
      assertEquals(cities, (result as Result.Success).data)
      coVerify(exactly = 1) { cityRepository.getAllCities(query) }
    }

  @Test
  fun `execute when repository returns empty list then returns success with empty list`() =
    runTest(testDispatcher) {
      // Given
      val query = "NonExistentCity"
      coEvery { cityRepository.getAllCities(query) } returns emptyList()

      // When
      val result = searchCitiesUseCase(query)

      // Then
      assertTrue(result is Result.Success)
      assertTrue((result as Result.Success).data.isEmpty())
      coVerify(exactly = 1) { cityRepository.getAllCities(query) }
    }

  @Test
  fun `execute when repository throws exception then returns failure`() = runTest(testDispatcher) {
    // Given
    val query = "London"
    val exception = IOException("Network error")
    coEvery { cityRepository.getAllCities(query) } throws exception

    // When
    val result = searchCitiesUseCase(query)

    // Then
    assertTrue(result is Result.Failure)
    assertEquals(exception, (result as Result.Failure).error)
    coVerify(exactly = 1) { cityRepository.getAllCities(query) }
  }

  @Test
  fun `execute when query is empty then returns empty list`() = runTest(testDispatcher) {
    // Given
    val query = ""
    coEvery { cityRepository.getAllCities(query) } returns emptyList()

    // When
    val result = searchCitiesUseCase(query)

    // Then
    assertTrue(result is Result.Success)
    assertTrue((result as Result.Success).data.isEmpty())
    coVerify(exactly = 1) { cityRepository.getAllCities(query) }
  }
}
