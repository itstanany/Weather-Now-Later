package com.itstanany.data.city.repositories

import app.cash.turbine.test
import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.data.city.models.CityResultDto
import com.itstanany.data.city.remote.CityRemoteDataSource
import com.itstanany.data.mapper.CityMapper
import com.itstanany.domain.city.models.City
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CityRepositoryImplTest {
  @MockK
  private lateinit var cityLocalDataSource: CityLocalDataSource

  @MockK
  private lateinit var cityRemoteDataSource: CityRemoteDataSource

  @MockK
  private lateinit var cityMapper: CityMapper

  private lateinit var cityRepository: CityRepositoryImpl

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    cityRepository = CityRepositoryImpl(cityLocalDataSource, cityRemoteDataSource, cityMapper)
  }

  @Test
  fun `getAllCities when remote returns valid cities then returns mapped cities`() = runTest {
    // Given
    val cityName = "London"
    val mappedCity = City(name = "London", country = "UK", latitude = 51.5074f, longitude =  -0.1278f, id = 1)
    val cityDto = CityResultDto(
      name = "London",
      country = "UK",
      latitude = 51.5074,
      longitude = -0.1278,
      admin1 = null,
      admin1Id = null,
      admin2 = null,
      admin2Id = null,
      admin3 = null,
      admin3Id = null,
      admin4 = null,
      admin4Id = null,
      countryCode = null,
      countryId = null,
      elevation = null,
      featureCode = null,
      id = null,
      population = null,
      postcodes = listOf(),
      timezone = null,
    )
    coEvery {
      cityRemoteDataSource.searchCities(cityName)
    } returns listOf(cityDto)
    every { cityMapper.mapToDomain(cityDto) } returns mappedCity

    // When
    val result = cityRepository.getAllCities(cityName)

    // Then
    assertEquals(1, result.size)
    assertEquals("London", result[0].name)
    assertEquals("UK", result[0].country)
    assertEquals(51.5074f, result[0].latitude)
    assertEquals(-0.1278f, result[0].longitude)
    coVerify(exactly = 1) { cityRemoteDataSource.searchCities(cityName) }
    verify(exactly = 1) { cityMapper.mapToDomain(cityDto) }

  }

  @Test
  fun `getAllCities when remote returns cities with null coordinates then filters them out`() =
    runTest {
      // Given
      val cityName = "London"
      val validCityDto = makeValidCityDto()
      val invalidCityDto = makeInvalidCityDtoNullCoordinates()
      coEvery {
        cityRemoteDataSource.searchCities(cityName)
      } returns listOf(validCityDto, invalidCityDto)
      every { cityMapper.mapToDomain(validCityDto) } returns makeValidMappedCity()
      every { cityMapper.mapToDomain(invalidCityDto) } returns null
      // When
      val result = cityRepository.getAllCities(cityName)

      // Then
      assertEquals(1, result.size)
      assertEquals("London", result[0].name)
      coVerify(exactly = 1) { cityRemoteDataSource.searchCities(cityName) }
      verify(exactly = 1) { cityMapper.mapToDomain(validCityDto) }
      verify(exactly = 0) { cityMapper.mapToDomain(invalidCityDto) }
    }

  @Test
  fun `getLastSearchedCity returns flow from local data source`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    every { cityLocalDataSource.getLastSearchedCity() } returns flowOf(city)

    // When & Then
    cityRepository.getLastSearchedCity().test {
      assertEquals(city, awaitItem())
      awaitComplete()
    }
    verify(exactly = 1) { cityLocalDataSource.getLastSearchedCity() }
  }

  @Test
  fun `saveLastSearchedCity delegates to local data source`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    coEvery { cityLocalDataSource.saveCity(city) } just Runs

    // When
    cityRepository.saveLastSearchedCity(city)

    // Then
    coVerify(exactly = 1) { cityLocalDataSource.saveCity(city) }
  }

  private fun makeValidCityDto() = CityResultDto(
    name = "London",
    country = "United Kingdom",
    latitude = 51.5074,
    longitude = -0.1278,
    admin1 = "England",
    admin1Id = 1,
    admin2 = "Greater London",
    admin2Id = 2,
    admin3 = "City of London",
    admin3Id = 3,
    admin4 = null,
    admin4Id = null,
    countryCode = "GB",
    countryId = 826,
    elevation = 11.0,
    featureCode = "PPLC",
    id = 2643743,
    population = 8961989,
    postcodes = listOf("EC1A", "SW1A"),
    timezone = "Europe/London"
  )

  private fun makeValidMappedCity() = City(
    name = "London",
    country = "United Kingdom",
    latitude = 51.5074f,
    longitude = -0.1278f,
    id = 1
  )
  // Invalid instance with null coordinates
  private fun makeInvalidCityDtoNullCoordinates() = CityResultDto(
    name = "London",
    country = "United Kingdom",
    latitude = null,
    longitude = null,
    admin1 = null,
    admin1Id = null,
    admin2 = null,
    admin2Id = null,
    admin3 = null,
    admin3Id = null,
    admin4 = null,
    admin4Id = null,
    countryCode = null,
    countryId = null,
    elevation = null,
    featureCode = null,
    id = null,
    population = null,
    postcodes = null,
    timezone = null
  )

}
