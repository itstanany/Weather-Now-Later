package com.itstanany.features.current_weather.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.common.Result
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.domain.weather.usecases.GetCurrentWeatherUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate

//TODO: add more test cases
@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest {
  @MockK
  private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

  @MockK
  private lateinit var getLastSearchedCityUseCase: GetLastSearchedCityUseCase

  private lateinit var viewModel: CurrentWeatherViewModel
  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = CurrentWeatherViewModel(getCurrentWeatherUseCase, getLastSearchedCityUseCase)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `handleScreenOpened loads weather for last searched city successfully`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val weather = createMockWeather()
    coEvery { getLastSearchedCityUseCase() } returns flowOf(city)
    coEvery { getCurrentWeatherUseCase(city) } returns Result.Success(
      weather
    )

    // When
    viewModel.handleScreenOpened()
    testDispatcher.scheduler.advanceUntilIdle()

    // Then
    with(viewModel.viewState.value) {
      assertEquals(city, city)
      assertEquals(weather.maxTemp, maxTemp)
      assertEquals(weather.minTemp, minTemp)
      assertEquals(weather.condition, condition)
      assertFalse(isLoading)
      assertNull(error)
    }
  }

  @Test
  fun `handleScreenOpened called multiple times only loads weather once`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    coEvery { getLastSearchedCityUseCase() } returns flowOf(city)
    coEvery { getCurrentWeatherUseCase(city) } returns Result.Success(createMockWeather())

    // When
    viewModel.handleScreenOpened()
    viewModel.handleScreenOpened()
    testDispatcher.scheduler.advanceUntilIdle()

    // Then
    coVerify(exactly = 1) { getLastSearchedCityUseCase() }
  }

  @Test
  fun `handleScreenOpened with no last searched city shows error`() = runTest {
    // Given
    coEvery { getLastSearchedCityUseCase() } returns flowOf(null)

    // When
    viewModel.handleScreenOpened()
    testDispatcher.scheduler.advanceUntilIdle()

    // Then
    with(viewModel.viewState.value) {
      assertEquals("No last searched city", error)
      assertFalse(isLoading)
    }
  }

  @Test
  fun `handleScreenOpened with weather fetch failure shows error`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val error = IOException("Network error")
    coEvery { getLastSearchedCityUseCase() } returns flowOf(city)
    coEvery { getCurrentWeatherUseCase(city) } returns Result.Failure(error)

    // When
    viewModel.handleScreenOpened()
    testDispatcher.scheduler.advanceUntilIdle()

    // Then
    with(viewModel.viewState.value) {
      assertEquals("Network error", error.message)
      assertFalse(isLoading)
    }
  }

  private fun createMockWeather() = DailyWeather(
    maxTemp = 20.0,
    minTemp = 15.0,
    condition = WeatherCondition.Sunny("Clear sky"),
    maxTempUnit = "°C",
    minTempUnit = "°C",
    maxApparentTemp = 22.0,
    minApparentTemp = 13.0,
    maxWindSpeed = 10.0,
    maxWindSpeedUnit = "km/h",
    date = LocalDate.now(),
    maxApparentTempUnit = "",
    minApparentTempUnit = ""
  )

}
