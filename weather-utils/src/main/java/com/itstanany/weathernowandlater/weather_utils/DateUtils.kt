package com.itstanany.weathernowandlater.weather_utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateUtils {
  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  /**
   * Converts a date string in the format 'yyyy-MM-dd' to LocalDate.
   *
   * @param dateString The date string to be converted.
   * @return The LocalDate if parsing succeeds, or null if parsing fails.
   */
  fun parseDate(dateString: String): LocalDate {
    return try {
      LocalDate.parse(dateString, dateFormatter)
    } catch (e: DateTimeParseException) {
      throw e
    }
  }
}

