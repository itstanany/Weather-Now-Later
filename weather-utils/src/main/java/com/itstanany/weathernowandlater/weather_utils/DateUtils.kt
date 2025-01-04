package com.itstanany.weathernowandlater.weather_utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Utility object for date parsing and formatting operations.
 * Provides methods to parse date strings and format dates into specific patterns.
 */
object DateUtils {
  /** Formatter for parsing dates in 'yyyy-MM-dd' format */
  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  /** Formatter for converting dates to 'EEEE, MMM d' format (e.g., "Monday, Jan 15") */
  private val dayFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d")

  /**
   * Converts a date string in the format 'yyyy-MM-dd' to LocalDate.
   *
   * @param dateString The date string to be converted.
   * @return The LocalDate if parsing succeeds.
   * @throws DateTimeParseException if the date string cannot be parsed.
   */
  fun parseDate(dateString: String): LocalDate {
    return try {
      LocalDate.parse(dateString, dateFormatter)
    } catch (e: DateTimeParseException) {
      throw e
    }
  }

  /**
   * Formats a LocalDate to a string in the format 'EEEE, MMM d' (e.g., "Monday, Jan 15").
   *
   * @param date The LocalDate to format.
   * @return A formatted string representing the date.
   */
  fun formatToWeekDayAndMonth(date: LocalDate): String {
    return date.format(dayFormatter)
  }
}

