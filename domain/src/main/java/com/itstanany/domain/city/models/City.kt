package com.itstanany.domain.city.models

import kotlinx.serialization.Serializable

@Serializable
data class City(
  val name: String,
  val country: String,
  val longitude: Float,
  val latitude: Float,
  val id: Int,
)
