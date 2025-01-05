package com.itstanany.data.mapper

import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class WeatherMapperTest {
  private lateinit var weatherMapper: WeatherMapper
  private val testDate = LocalDate.of(2024, 1, 5)

  @Before
  fun setup() {
    weatherMapper = WeatherMapper()
  }

  @Test
  fun `mapToDomain with all valid fields returns correctly mapped weather`() {
    // When
    val result = weatherMapper.mapToDomain(
      weatherCode = 0,
      maxTemp = 20.0,
      minTemp = 15.0,
      maxApparentTemp = 22.0,
      minApparentTemp = 13.0,
      maxWindSpeed = 10.0,
      minTempUnit = "°C",
      maxTempUnit = "°C",
      minApparentTempUnit = "°C",
      maxApparentTempUnit = "°C",
      maxWindSpeedUnit = "km/h",
      date = testDate
    )

    // Then
    assertWeatherResult(result, WeatherCondition.Sunny("Clear sky"))
  }

  @Test
  fun `mapToDomain with different weather codes returns correct conditions`() {
    // Test Sunny condition
    val sunnyResult = createWeatherWithCode(0)
    assertEquals(WeatherCondition.Sunny("Clear sky"), sunnyResult.condition)

    // Test Cloudy condition
    val cloudyResult = createWeatherWithCode(45)
    assertEquals(WeatherCondition.Cloudy("Fog"), cloudyResult.condition)

    // Test Rainy condition
    val rainyResult = createWeatherWithCode(61)
    assertEquals(WeatherCondition.Rainy("Rain: Slight intensity"), rainyResult.condition)
  }

  @Test
  fun `mapToDomain with null optional fields returns weather with default units`() {
    // When
    val result = weatherMapper.mapToDomain(
      weatherCode = 0,
      maxTemp = 20.0,
      minTemp = 15.0,
      maxApparentTemp = null,
      minApparentTemp = null,
      maxWindSpeed = null,
      minTempUnit = null,
      maxTempUnit = null,
      minApparentTempUnit = null,
      maxApparentTempUnit = null,
      maxWindSpeedUnit = null,
      date = testDate
    )

    // Then
    with(result) {
      assertEquals("Unit Unavailable", maxTempUnit)
      assertEquals("Unit Unavailable", minTempUnit)
      assertEquals("Unit Unavailable", maxApparentTempUnit)
      assertEquals("Unit Unavailable", minApparentTempUnit)
      assertEquals("Unit Unavailable", maxWindSpeedUnit)
      assertNull(maxApparentTemp)
      assertNull(minApparentTemp)
      assertNull(maxWindSpeed)
    }
  }

  private fun createWeatherWithCode(code: Int) = weatherMapper.mapToDomain(
    weatherCode = code,
    maxTemp = 20.0,
    minTemp = 15.0,
    maxApparentTemp = 22.0,
    minApparentTemp = 13.0,
    maxWindSpeed = 10.0,
    minTempUnit = "°C",
    maxTempUnit = "°C",
    minApparentTempUnit = "°C",
    maxApparentTempUnit = "°C",
    maxWindSpeedUnit = "km/h",
    date = testDate
  )

  private fun assertWeatherResult(result: DailyWeather, expectedCondition: WeatherCondition) {
    with(result) {
      assertEquals(20.0, maxTemp)
      assertEquals(15.0, minTemp)
      assertEquals(22.0, maxApparentTemp)
      assertEquals(13.0, minApparentTemp)
      assertEquals(10.0, maxWindSpeed)
      assertEquals("°C", maxTempUnit)
      assertEquals("°C", minTempUnit)
      assertEquals("°C", maxApparentTempUnit)
      assertEquals("°C", minApparentTempUnit)
      assertEquals("km/h", maxWindSpeedUnit)
      assertEquals(expectedCondition, condition)
      assertEquals(testDate, date)
    }
  }
}
