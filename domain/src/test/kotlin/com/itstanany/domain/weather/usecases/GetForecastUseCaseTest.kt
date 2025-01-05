package com.itstanany.domain.weather.usecases

import com.itstanany.domain.city.models.City
import com.itstanany.domain.common.DispatcherProvider
import com.itstanany.domain.common.Result
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.domain.weather.repositories.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate

class GetForecastUseCaseTest {
  @MockK
  private lateinit var weatherRepository: WeatherRepository
  private lateinit var getForecastUseCase: GetForecastUseCase
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
    getForecastUseCase = GetForecastUseCase(weatherRepository, dispatcherProvider)
  }

  @Test
  fun `execute returns success with forecast list when repository succeeds`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val forecast = List(7) { createMockWeather() }
    coEvery { weatherRepository.getForecast(city) } returns forecast

    // When
    val result = getForecastUseCase(city)

    // Then
    assertTrue(result is Result.Success)
    assertEquals(forecast, (result as Result.Success).data)
    coVerify(exactly = 1) { weatherRepository.getForecast(city) }
  }

  @Test
  fun `execute returns failure when repository throws exception`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val exception = IOException("Network error")
    coEvery { weatherRepository.getForecast(city) } throws exception

    // When
    val result = getForecastUseCase(city)

    // Then
    assertTrue(result is Result.Failure)
    assertEquals(exception, (result as Result.Failure).error)
    coVerify(exactly = 1) { weatherRepository.getForecast(city) }
  }

  @Test
  fun `execute returns failure when repository returns empty list`() = runTest(testDispatcher) {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    coEvery { weatherRepository.getForecast(city) } returns emptyList()

    // When
    val result = getForecastUseCase(city)

    // Then
    assertTrue(result is Result.Success)
    assertEquals(emptyList<DailyWeather>(), (result as Result.Success).data)
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
    maxApparentTempUnit = null, minApparentTempUnit = null,

    )
}
