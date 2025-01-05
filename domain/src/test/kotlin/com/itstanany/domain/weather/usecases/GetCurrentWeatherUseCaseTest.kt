package com.itstanany.domain.weather.usecases

import com.itstanany.domain.city.models.City
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.domain.weather.repositories.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetCurrentWeatherUseCaseTest {
  @MockK
  private lateinit var weatherRepository: WeatherRepository

  private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

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
    getCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository, dispatcherProvider)
  }

  @Test
  fun `when repository returns success then return success result`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val weatherDate = LocalDate.of(2023, 10, 25)
    val expectedWeather = DailyWeather(
      maxTemp = 20.0,
      minTemp = 15.0,
      condition = WeatherCondition.Sunny(),
      maxWindSpeed = 10.0,
      maxTempUnit = "eleifend",
      minTempUnit = "ultricies",
      maxApparentTemp = null,
      maxApparentTempUnit = null,
      minApparentTemp = null,
      minApparentTempUnit = null,
      maxWindSpeedUnit = null,
      date = weatherDate,

      )
    coEvery { weatherRepository.getCurrentWeather(city) } returns expectedWeather

    // When
    val result = getCurrentWeatherUseCase(city)

    // Then
    assertTrue(result is com.itstanany.domain.common.Result.Success)
    assertEquals(expectedWeather, (result as com.itstanany.domain.common.Result.Success).data)
    coVerify(exactly = 1) { weatherRepository.getCurrentWeather(city) }
  }

  @Test
  fun `when repository throws exception then return failure result`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val exception = Exception("Network error")
    coEvery { weatherRepository.getCurrentWeather(city) } throws exception

    // When
    val result = getCurrentWeatherUseCase(city)

    // Then
    assertTrue(result is com.itstanany.domain.common.Result.Failure)
    assertEquals(exception, (result as com.itstanany.domain.common.Result.Failure).error)
    coVerify(exactly = 1) { weatherRepository.getCurrentWeather(city) }
  }
}
