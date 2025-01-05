package com.itstanany.domain.city.models

import kotlinx.serialization.Serializable

/**
 * Represents a city with its name, country, geographical coordinates, and ID.
 *
 * This data class is used to store information about a city, including its name,
 * country, longitude, latitude, and a unique identifier. It is serializable,
 * allowing it to be easily converted to and from different formats, such as JSON.
 *
 * @property name The name of the city.
 * @property country The country where the city is located.
 * @property longitude The longitude of the city.
 * @property latitude The latitude of the city.
 * @property id The unique identifier of the city.
 */
@Serializable
data class City(
  val name: String,
  val country: String,
  val longitude: Float,
  val latitude: Float,
  val id: Int,
)
