package com.itstanany.domain.city.usecases

import app.cash.turbine.test
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.repositories.CityRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetLastSearchedCityUseCaseTest {
  @MockK
  private lateinit var cityRepository: CityRepository

  private lateinit var getLastSearchedCityUseCase: GetLastSearchedCityUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    getLastSearchedCityUseCase = GetLastSearchedCityUseCase(cityRepository)
  }

  @Test
  fun `invoke when repository has stored city then returns city flow`() = runTest {
    // Given
    val expectedCity = City("London", "UK", 51.5074f, -0.1278f, 1)
    every { cityRepository.getLastSearchedCity() } returns flowOf(expectedCity)

    // When
    val result = getLastSearchedCityUseCase()

    // Then
    result.test {
      assertEquals(expectedCity, awaitItem())
      awaitComplete()
    }
    verify(exactly = 1) { cityRepository.getLastSearchedCity() }
  }

  @Test
  fun `invoke when repository returns null then returns null flow`() = runTest {
    // Given
    every { cityRepository.getLastSearchedCity() } returns flowOf(null)

    // When
    val result = getLastSearchedCityUseCase()

    // Then
    result.test {
      assertNull(awaitItem())
      awaitComplete()
    }
    verify(exactly = 1) { cityRepository.getLastSearchedCity() }
  }

  @Test
  fun `invoke when repository throws exception then propagates error`() = runTest {
    // Given
    val exception = RuntimeException("Database error")
    every { cityRepository.getLastSearchedCity() } returns flow { throw exception }

    // When & Then
    val result = getLastSearchedCityUseCase()
    result.test {
      assertEquals(exception, awaitError())
    }
    verify(exactly = 1) { cityRepository.getLastSearchedCity() }
  }
}

