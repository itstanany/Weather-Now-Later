package com.itstanany.data.weather.repositories

import com.itstanany.data.weather.models.Daily
import com.itstanany.data.weather.models.DailyUnits
import com.itstanany.data.weather.models.WeatherForecastResponse
import com.itstanany.data.weather.remote.WeatherRemoteDataSource
import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.WeatherCondition
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
  @MockK
  private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource

  private lateinit var weatherRepository: WeatherRepositoryImpl

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    weatherRepository = WeatherRepositoryImpl(weatherRemoteDataSource)
  }

  @Test
  fun `getCurrentWeather when response is valid then returns mapped weather`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val response = mockValidForecastResponse()
    coEvery {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    } returns response

    // When
    val result = weatherRepository.getCurrentWeather(city)

    // Then
    assertEquals(20.0, result.maxTemp)
    assertEquals(15.0, result.minTemp)
    assertEquals(WeatherCondition.Sunny(), result.condition)
    assertEquals("°C", result.maxTempUnit)
    coVerify(exactly = 1) {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    }
  }

  @Test
  fun `getCurrentWeather when response is invalid then throws exception`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val response = mockInvalidForecastResponse()
    coEvery {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    } returns response

    // When & Then
    assertThrows(Exception::class.java) {
      runBlocking { weatherRepository.getCurrentWeather(city) }
    }
    coVerify(exactly = 1) {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    }
  }

  @Test
  fun `getForecast when response is valid then returns mapped forecast list`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val response = mockValidForecastResponse()
    coEvery {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    } returns response

    // When
    val result = weatherRepository.getForecast(city)

    // Then
    assertEquals(7, result.size)
    assertEquals(20.0, result[0].maxTemp)
    assertEquals(15.0, result[0].minTemp)
    assertEquals(WeatherCondition.Sunny("Clear sky"), result[0].condition)
    coVerify(exactly = 1) {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    }
  }

  @Test
  fun `getForecast when response is invalid then throws exception`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    val invalidResponse = mockInvalidForecastResponse()
    coEvery {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    } returns invalidResponse

    // When & Then
    val thrown = assertThrows(Exception::class.java) {
      runBlocking {
        weatherRepository.getForecast(city)
      }
    }
    assertEquals("Invalid response", thrown.message)
    coVerify(exactly = 1) {
      weatherRemoteDataSource.getForecast(city.latitude, city.longitude)
    }
  }

  private fun mockValidForecastResponse() = WeatherForecastResponse(
    daily = Daily(
      time = List(7) { "2024-01-05" },
      weatherCode = List(7) { 0 },
      temperature2mMax = List(7) { 20.0 },
      temperature2mMin = List(7) { 15.0 },
      apparentTemperatureMax = List(7) { 22.0 },
      apparentTemperatureMin = List(7) { 13.0 },
      windSpeed10mMax = List(7) { 10.0 },
      sunrise = listOf(
        "2025-01-05T07:15",
        "2025-01-06T07:15",
        "2025-01-07T07:15",
        "2025-01-08T07:14",
        "2025-01-09T07:14",
        "2025-01-10T07:13",
        "2025-01-11T07:12"
      ),
      sunset = listOf(
        "2025-01-05T15:07",
        "2025-01-06T15:08",
        "2025-01-07T15:10",
        "2025-01-08T15:11",
        "2025-01-09T15:12",
        "2025-01-10T15:14",
        "2025-01-11T15:15"
      ),
      windDirection10mDominant = listOf(
        140, 196, 224, 224, 260, 280, 287
      )
    ),
    dailyUnits = DailyUnits(
      apparentTemperatureMax = "°C",
      apparentTemperatureMin = "°C",
      sunrise = "iso8601",
      sunset = "iso8601",
      temperature2mMax = "°C",
      temperature2mMin = "°C",
      time = "iso8601",
      weatherCode = "wmo code",
      windDirection10mDominant = "°",
      windSpeed10mMax = "km/h",
    ),
    elevation = null,
    generationtimeMs = null,
    latitude = null,
    longitude = null,
    timezone = null,
    timezoneAbbreviation = null,
    utcOffsetSeconds = null,
  )
  private fun mockInvalidForecastResponse() = WeatherForecastResponse(
    daily = Daily(
      time = null,
      weatherCode = null,
      temperature2mMax = null,
      temperature2mMin = null,
      apparentTemperatureMax = null,
      apparentTemperatureMin = null,
      windSpeed10mMax = null,
      sunrise = listOf(),
      sunset = listOf(),
      windDirection10mDominant = listOf(),
    ),
    dailyUnits = null,
    elevation = null,
    generationtimeMs = null,
    latitude = null,
    longitude = null,
    timezone = null,
    timezoneAbbreviation = null,
    utcOffsetSeconds = null

  )
}
