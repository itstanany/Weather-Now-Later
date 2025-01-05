package com.itstanany.data.city.remote

import com.itstanany.data.city.models.CityResultDto
import com.itstanany.data.city.models.SearchCitiesResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException


class CityRemoteDataSourceImplTest {
  @MockK
  private lateinit var cityRemoteApiService: CityRemoteApiService

  private lateinit var cityRemoteDataSource: CityRemoteDataSourceImpl

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    cityRemoteDataSource = CityRemoteDataSourceImpl(cityRemoteApiService)
  }

  @Test
  fun `searchCities when API returns valid results then returns city list`() = runTest {
    // Given
    val query = "London"
    val cityDto = CityResultDto(
      name = "London",
      country = "UK",
      latitude = 51.5074,
      longitude = -0.1278,
      admin1 = "England",
      admin1Id = 1,
      admin2 = null,
      admin2Id = null,
      admin3 = null,
      admin3Id = null,
      admin4 = null,
      admin4Id = null,
      countryCode = "GB",
      countryId = 1,
      elevation = 11.0,
      featureCode = "PPLC",
      id = 2643743,
      population = 8961989,
      postcodes = listOf("EC1A", "SW1A"),
      timezone = "Europe/London"
    )
    val response = SearchCitiesResponse(
      results = listOf(cityDto),
      generationTimeMs = 0.0,
    )
    coEvery { cityRemoteApiService.searchCities(query) } returns response

    // When
    val result = cityRemoteDataSource.searchCities(query)

    // Then
    assertEquals(listOf(cityDto), result)
    coVerify(exactly = 1) { cityRemoteApiService.searchCities(query) }
  }

  @Test
  fun `searchCities when API returns null results then returns null`() = runTest {
    // Given
    val query = "NonExistentCity"
    val response = SearchCitiesResponse(results = null, generationTimeMs = null)
    coEvery { cityRemoteApiService.searchCities(query) } returns response

    // When
    val result = cityRemoteDataSource.searchCities(query)

    // Then
    assertNull(result)
    coVerify(exactly = 1) { cityRemoteApiService.searchCities(query) }
  }

  @Test
  fun `searchCities when API returns empty results then returns empty list`() = runTest {
    // Given
    val query = "NonExistentCity"
    val response = SearchCitiesResponse(results = emptyList(), generationTimeMs = null)
    coEvery { cityRemoteApiService.searchCities(query) } returns response

    // When
    val result = cityRemoteDataSource.searchCities(query)

    // Then
    assertTrue(result?.isEmpty() == true)
    coVerify(exactly = 1) { cityRemoteApiService.searchCities(query) }
  }

  @Test
  fun `searchCities when API throws exception then propagates error`() = runTest {
    // Given
    val query = "London"
    val exception = IOException("Network error")
    coEvery { cityRemoteApiService.searchCities(query) } throws exception

    // When & Then
    val thrown = assertThrows(IOException::class.java) {
      runBlocking {
        cityRemoteDataSource.searchCities(query)
      }
    }
    assertEquals("Network error", thrown.message)
    coVerify(exactly = 1) { cityRemoteApiService.searchCities(query) }
  }
}


