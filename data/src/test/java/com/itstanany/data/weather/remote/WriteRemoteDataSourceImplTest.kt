package com.itstanany.data.weather.remote

import com.itstanany.data.weather.models.Daily
import com.itstanany.data.weather.models.DailyUnits
import com.itstanany.data.weather.models.WeatherForecastResponse
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
import java.io.IOException

class WeatherRemoteDataSourceImplTest {
  @MockK
  private lateinit var weatherRemoteApiService: WeatherRemoteApiService

  private lateinit var weatherRemoteDataSource: WeatherRemoteDataSourceImpl

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    weatherRemoteDataSource = WeatherRemoteDataSourceImpl(weatherRemoteApiService)
  }

  @Test
  fun `getForecast when API call succeeds then returns forecast response`() = runTest {
    // Given
    val latitude = 51.5074f
    val longitude = -0.1278f
    val expectedResponse = WeatherForecastResponse(
      daily = Daily(
        time = listOf("2024-01-05"),
        weatherCode = listOf(0),
        temperature2mMax = listOf(20.0),
        temperature2mMin = listOf(15.0),
        apparentTemperatureMax = listOf(22.0),
        apparentTemperatureMin = listOf(13.0),
        windSpeed10mMax = listOf(10.0),
        sunrise = listOf(),
        sunset = listOf(),
        windDirection10mDominant = listOf(),

        ),
      dailyUnits = DailyUnits(
        temperature2mMax = "C",
        temperature2mMin = "C",
        apparentTemperatureMax = "C",
        apparentTemperatureMin = "C",
        windSpeed10mMax = "km/h",
        sunrise = null,
        sunset = null,
        time = null,
        weatherCode = null,
        windDirection10mDominant = null,
      ),
      elevation = null,
      generationtimeMs = null,
      latitude = null,
      longitude = null,
      timezone = null,
      timezoneAbbreviation = null,
      utcOffsetSeconds = null
    )

    coEvery {
      weatherRemoteApiService.getForecast(latitude, longitude)
    } returns expectedResponse

    // When
    val result = weatherRemoteDataSource.getForecast(latitude, longitude)

    // Then
    assertEquals(expectedResponse, result)
    coVerify(exactly = 1) { weatherRemoteApiService.getForecast(latitude, longitude) }
  }

  @Test
  fun `getForecast when API call fails then throws exception`() = runTest {
    // Given
    val latitude = 51.5074f
    val longitude = -0.1278f
    val exception = IOException("Network error")
    coEvery {
      weatherRemoteApiService.getForecast(latitude, longitude)
    } throws exception

    // When & Then
    val thrown = assertThrows(IOException::class.java) {
      runBlocking {
        weatherRemoteDataSource.getForecast(latitude, longitude)
      }
    }
    assertEquals("Network error", thrown.message)
    coVerify(exactly = 1) { weatherRemoteApiService.getForecast(latitude, longitude) }
  }

  @Test
  fun `getForecast when coordinates are invalid then throws exception`() = runTest {
    // Given
    val latitude = 91f  // Invalid latitude (>90)
    val longitude = 181f // Invalid longitude (>180)
    val exception = IllegalArgumentException("Invalid coordinates")
    coEvery {
      weatherRemoteApiService.getForecast(latitude, longitude)
    } throws exception

    // When & Then
    val thrown = assertThrows(IllegalArgumentException::class.java) {
      runBlocking {
        weatherRemoteDataSource.getForecast(latitude, longitude)
      }
    }
    assertEquals("Invalid coordinates", thrown.message)
    coVerify(exactly = 1) { weatherRemoteApiService.getForecast(latitude, longitude) }
  }
}
