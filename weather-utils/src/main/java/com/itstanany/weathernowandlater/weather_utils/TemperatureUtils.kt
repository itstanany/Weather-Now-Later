package com.itstanany.weathernowandlater.weather_utils

/**
 * Utility object for temperature conversions and formatting.
 * Provides methods to convert between Celsius, Fahrenheit, and Kelvin,
 * as well as formatting temperature values with units.
 */
object TemperatureUtils {
  /**
   * Converts a temperature from Celsius to Fahrenheit.
   * @param celsius The temperature in Celsius to convert
   * @return The temperature in Fahrenheit
   */
  fun celsiusToFahrenheit(celsius: Double): Double {
    return (celsius * 9/5) + 32
  }

  /**
   * Converts a temperature from Fahrenheit to Celsius.
   * @param fahrenheit The temperature in Fahrenheit to convert
   * @return The temperature in Celsius
   */
  fun fahrenheitToCelsius(fahrenheit: Double): Double {
    return (fahrenheit - 32) * 5/9
  }

  /**
   * Converts a temperature from Celsius to Kelvin.
   * @param celsius The temperature in Celsius to convert
   * @return The temperature in Kelvin
   */
  fun celsiusToKelvin(celsius: Double): Double {
    return celsius + 273.15
  }

  /**
   * Converts a temperature from Kelvin to Celsius.
   * @param kelvin The temperature in Kelvin to convert
   * @return The temperature in Celsius
   */
  fun kelvinToCelsius(kelvin: Double): Double {
    return kelvin - 273.15
  }

  /**
   * Formats a temperature value with its unit.
   * @param temperature The temperature value to format
   * @param unit The unit symbol to append (e.g., "C", "F", "K")
   * @return A formatted string with the temperature and unit (e.g., "23.5 C")
   */
  fun formatTemperature(temperature: Double, unit: String): String {
    return "%.1f $unit".format(temperature)
  }
}
