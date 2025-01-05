package com.itstanany.weathernowandlater.weather_utils

import junit.framework.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateUtilsTest {
  private val validDate = LocalDate.of(2024, 1, 15)
  private val validDateString = "2024-01-15"
  private val formattedDateString = "Monday, Jan 15"

  @Test
  fun `parseDate with valid date string returns correct LocalDate`() {
    val result = DateUtils.parseDate(validDateString)
    assertEquals(validDate, result)
  }

  @Test
  fun `parseDate with invalid format throws DateTimeParseException`() {
    val invalidFormats = listOf(
      "15-01-2024",
      "2024/01/15",
      "15/Jan/2024",
      "invalid-date"
    )

    invalidFormats.forEach { dateString ->
      assertThrows(DateTimeParseException::class.java) {
        DateUtils.parseDate(dateString)
      }
    }
  }

  @Test
  fun `parseDate with invalid date values throws DateTimeParseException`() {
    val invalidDates = listOf(
      "2024-13-15",  // Invalid month
      "2024-00-15",  // Invalid month
      "2024-01-32",  // Invalid day
      "2024-01-00"   // Invalid day
    )

    invalidDates.forEach { dateString ->
      assertThrows(DateTimeParseException::class.java) {
        DateUtils.parseDate(dateString)
      }
    }
  }


  @Test
  fun `formatToWeekDayAndMonth returns correctly formatted string`() {
    val result = DateUtils.formatToWeekDayAndMonth(validDate)
    assertEquals(formattedDateString, result)
  }

  @Test
  fun `formatToWeekDayAndMonth handles different dates correctly`() {
    val testCases = listOf(
      Triple(
        LocalDate.of(2024, 1, 1),
        "Monday, Jan 1",
        null
      ),
      Triple(
        LocalDate.of(2024, 12, 31),
        "Tuesday, Dec 31",
        null
      ),
      Triple(
        LocalDate.of(2024, 2, 29),
        "Thursday, Feb 29",
        null
      )
    )

    testCases.forEach { (date, expected) ->
      val result = DateUtils.formatToWeekDayAndMonth(date)
      assertEquals(expected, result)
    }
  }

  @Test
  fun `formatToWeekDayAndMonth handles leap year dates`() {
    val leapYearDate = LocalDate.of(2024, 2, 29)
    val result = DateUtils.formatToWeekDayAndMonth(leapYearDate)
    assertEquals("Thursday, Feb 29", result)
  }
}
