package com.itstanany.weathernowandlater.weather_utils

import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Test

class TemperatureUtilsTest {

  @Test
  fun `celsiusToFahrenheit converts temperatures correctly`() {
    val testCases = listOf(
      0.0 to 32.0,
      100.0 to 212.0,
      -40.0 to -40.0,
      37.0 to 98.6,
      -273.15 to -459.67
    )

    testCases.forEach { (celsius, expectedFahrenheit) ->
      val result = TemperatureUtils.celsiusToFahrenheit(celsius)
      assertEquals(expectedFahrenheit, result, 0.01)
    }
  }

  @Test
  fun `fahrenheitToCelsius converts temperatures correctly`() {
    val testCases = listOf(
      32.0 to 0.0,
      212.0 to 100.0,
      -40.0 to -40.0,
      98.6 to 37.0,
      -459.67 to -273.15
    )

    testCases.forEach { (fahrenheit, expectedCelsius) ->
      val result = TemperatureUtils.fahrenheitToCelsius(fahrenheit)
      assertEquals(expectedCelsius, result, 0.01)
    }
  }

  @Test
  fun `celsiusToKelvin converts temperatures correctly`() {
    val testCases = listOf(
      0.0 to 273.15,
      -273.15 to 0.0,
      100.0 to 373.15,
      37.0 to 310.15
    )

    testCases.forEach { (celsius, expectedKelvin) ->
      val result = TemperatureUtils.celsiusToKelvin(celsius)
      assertEquals(expectedKelvin, result, 0.01)
    }
  }

  @Test
  fun `kelvinToCelsius converts temperatures correctly`() {
    val testCases = listOf(
      273.15 to 0.0,
      0.0 to -273.15,
      373.15 to 100.0,
      310.15 to 37.0
    )

    testCases.forEach { (kelvin, expectedCelsius) ->
      val result = TemperatureUtils.kelvinToCelsius(kelvin)
      assertEquals(expectedCelsius, result, 0.01)
    }
  }


  @Test
  fun `formatTemperature formats positive temperatures correctly`() {
    val testCases = listOf(
      Triple(23.456, "C", "23.5 C"),
      Triple(98.6, "F", "98.6 F"),
      Triple(310.15, "K", "310.2 K")
    )

    testCases.forEach { (temp, unit, expected) ->
      val result = TemperatureUtils.formatTemperature(temp, unit)
      assertEquals(expected, result)
    }
  }

  @Test
  fun `formatTemperature formats negative temperatures correctly`() {
    val testCases = listOf(
      Triple(-40.0, "C", "-40.0 C"),
      Triple(-459.67, "F", "-459.7 F"),
      Triple(-273.15, "K", "-273.2 K")
    )

    testCases.forEach { (temp, unit, expected) ->
      val result = TemperatureUtils.formatTemperature(temp, unit)
      assertEquals(expected, result)
    }
  }

  @Test
  fun `formatTemperature formats zero correctly`() {
    val result = TemperatureUtils.formatTemperature(0.0, "C")
    assertEquals("0.0 C", result)
  }


  @Test
  fun `temperature conversions handle extreme values`() {
    val extremeValues = listOf(
      Double.MAX_VALUE,
      Double.MIN_VALUE,
      Double.POSITIVE_INFINITY,
      Double.NEGATIVE_INFINITY
    )

    extremeValues.forEach { value ->
      try {
        TemperatureUtils.celsiusToFahrenheit(value)
        TemperatureUtils.fahrenheitToCelsius(value)
        TemperatureUtils.celsiusToKelvin(value)
        TemperatureUtils.kelvinToCelsius(value)
      } catch (e: Exception) {
        fail("Should not have thrown any exception")
      }
    }
  }

  @Test
  fun `formatTemperature handles special values`() {
    val specialCases = listOf(
      Triple(Double.POSITIVE_INFINITY, "C", "Infinity C"),
      Triple(Double.NEGATIVE_INFINITY, "C", "-Infinity C"),
      Triple(Double.NaN, "C", "NaN C")
    )

    specialCases.forEach { (temp, unit, expected) ->
      val result = TemperatureUtils.formatTemperature(temp, unit)
      assertEquals(expected, result)
    }
  }
}
