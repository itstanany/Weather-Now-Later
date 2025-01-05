package com.itstanany.domain.common

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import org.junit.Test

class UtilsTest {
  @Test
  fun `safeCall when block executes successfully then returns Success`() {
    // Given
    val expectedResult = "test"

    // When
    val result = Utils.safeCall { expectedResult }

    // Then
    assertTrue(result is Result.Success)
    assertEquals(expectedResult, (result as Result.Success).data)
  }

  @Test
  fun `safeCall when block throws exception then returns Failure`() {
    // Given
    val expectedException = RuntimeException("Test exception")

    // When
    val result = Utils.safeCall { throw expectedException }

    // Then
    assertTrue(result is Result.Failure)
    assertEquals(expectedException, (result as Result.Failure).error)
  }

  @Test
  fun `safeCall when block returns null then returns Success with null`() {
    // When
    val result = Utils.safeCall { null }

    // Then
    assertTrue(result is Result.Success)
    assertNull((result as Result.Success).data)
  }

}

