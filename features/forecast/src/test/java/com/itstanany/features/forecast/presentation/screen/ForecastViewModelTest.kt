package com.itstanany.features.forecast.presentation.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.common.Result
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.domain.weather.usecases.GetForecastUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTest {
  @MockK
  private lateinit var getForecastUseCase: GetForecastUseCase

  @MockK
  private lateinit var getLastSearchedCityUseCase: GetLastSearchedCityUseCase

  private lateinit var viewModel: ForecastViewModel

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = StandardTestDispatcher(testScheduler)

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = ForecastViewModel(getForecastUseCase, getLastSearchedCityUseCase)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `processIntent when screen opened first time then loads forecast`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val forecasts = listOf(
      DailyWeather(
        maxTemp = 20.0,
        minTemp = 15.0,
        condition = WeatherCondition.Sunny(),
        maxWindSpeed = 10.0,
        date = LocalDate.now(),
        maxTempUnit = "dignissim",
        minTempUnit = "tempus",
        maxApparentTemp = null,
        maxApparentTempUnit = null,
        minApparentTemp = null,
        minApparentTempUnit = null,
        maxWindSpeedUnit = null,

        )
    )
    every { getLastSearchedCityUseCase() } returns flowOf(city)
    coEvery { getForecastUseCase(city) } returns Result.Success(forecasts)

    // When
    viewModel.processIntent(ForecastIntent.ScreenOpened)
    testScheduler.advanceUntilIdle()

    // Then
    with(viewModel.state.value) {
      assertFalse(isLoading)
      assertEquals(city, this.city)
      assertEquals(forecasts, this.forecasts)
      assertNull(error)
    }
  }

  @Test
  fun `processIntent when screen opened multiple times then loads forecast once`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    every { getLastSearchedCityUseCase() } returns flowOf(city)
    coEvery { getForecastUseCase(city) } returns Result.Success(emptyList())

    // When
    viewModel.processIntent(ForecastIntent.ScreenOpened)
    viewModel.processIntent(ForecastIntent.ScreenOpened)
    testScheduler.advanceUntilIdle()

    // Then
    coVerify(exactly = 1) { getForecastUseCase(city) }
  }

  @Test
  fun `loadForecast when no city found then updates error state`() = runTest {
    // Given
    every { getLastSearchedCityUseCase() } returns flowOf(null)

    // When
    viewModel.processIntent(ForecastIntent.ScreenOpened)
    testScheduler.advanceUntilIdle()

    // Then
    with(viewModel.state.value) {
      assertFalse(isLoading)
      assertEquals("No city found", error)
      assertTrue(forecasts.isEmpty())
    }
  }

  @Test
  fun `loadForecast when forecast fails then updates error state`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val error = Exception("Network error")
    every { getLastSearchedCityUseCase() } returns flowOf(city)
    coEvery { getForecastUseCase(city) } returns Result.Failure(error)

    // When
    viewModel.processIntent(ForecastIntent.ScreenOpened)
    testScheduler.advanceUntilIdle()

    // Then
    with(viewModel.state.value) {
      assertFalse(isLoading)
      assertEquals("Network error", error.message)
      assertEquals(city, this.city)
      assertTrue(forecasts.isEmpty())
    }
  }

  @Test
  fun `loadForecast when city flow throws then updates error state`() = runTest {
    // Given
    val error = Exception("Database error")
    every { getLastSearchedCityUseCase() } returns flow { throw error }

    // When
    viewModel.processIntent(ForecastIntent.ScreenOpened)
    testScheduler.advanceUntilIdle()

    // Then
    with(viewModel.state.value) {
      assertFalse(isLoading)
      assertEquals("Database error", error.message)
      assertTrue(forecasts.isEmpty())
    }
  }
}
