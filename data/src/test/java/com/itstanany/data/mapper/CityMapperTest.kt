package com.itstanany.data.mapper

import com.itstanany.data.city.models.CityResultDto
import com.itstanany.domain.city.models.City
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test


class CityMapperTest {
  private lateinit var cityMapper: CityMapper

  @Before
  fun setup() {
    cityMapper = CityMapper()
  }


  @Test
  fun `mapToDomain with valid fields returns mapped city`() {
    // When
    val result = cityMapper.mapToDomain(createValidCityDto())

    // Then
    assertValidCity(result)
  }

  @Test
  fun `mapToDomain with null id returns city with default id`() {
    // When
    val result = cityMapper.mapToDomain(createValidCityDto(id = null))

    // Then
    assertNotNull(result)
    assertEquals(0, result?.id)
  }

  @Test
  fun `mapToDomain with null latitude returns null`() {
    val result = cityMapper.mapToDomain(createValidCityDto(latitude = null))
    assertNull(result)
  }

  @Test
  fun `mapToDomain with null longitude returns null`() {
    val result = cityMapper.mapToDomain(createValidCityDto(longitude = null))
    assertNull(result)
  }

  @Test
  fun `mapToDomain with invalid name returns null`() {
    assertNull(cityMapper.mapToDomain(createValidCityDto(name = null)))
    assertNull(cityMapper.mapToDomain(createValidCityDto(name = "")))
  }

  @Test
  fun `mapToDomain with invalid country returns null`() {
    assertNull(cityMapper.mapToDomain(createValidCityDto(country = null)))
    assertNull(cityMapper.mapToDomain(createValidCityDto(country = "")))
  }

  private companion object {
    fun createValidCityDto(
      name: String? = "London",
      country: String? = "UK",
      latitude: Double? = 51.5074,
      longitude: Double? = -0.1278,
      id: Int? = 1
    ) = CityResultDto(
      name = name,
      country = country,
      latitude = latitude,
      longitude = longitude,
      id = id,
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
      population = null,
      postcodes = listOf(),
      timezone = null,

    )

    fun assertValidCity(result: City?) {
      assertNotNull(result)
      with(result!!) {
        assertEquals("London", name)
        assertEquals("UK", country)
        assertEquals(51.5074f, latitude)
        assertEquals(-0.1278f, longitude)
        assertEquals(1, id)
      }
    }
  }
}

